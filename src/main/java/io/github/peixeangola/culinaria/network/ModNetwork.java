package io.github.peixeangola.culinaria.network;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.block.CookingStationBlockEntity;
import io.github.peixeangola.culinaria.block.StorageBlockEntity;
import io.github.peixeangola.culinaria.catalog.NearbyCounts;
import io.github.peixeangola.culinaria.catalog.RecipeCatalog;
import io.github.peixeangola.culinaria.recipe.CookingRecipe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

/**
 * Canal do Culinária. Três mensagens:
 * <ul>
 *   <li>{@link Catalog} (servidor → cliente): as receitas culinaria:cooking, ao entrar e depois de /reload, para o livro e
 *   as interfaces (o 26.3 não manda receitas ao cliente);</li>
 *   <li>{@link NearbyRequest} (cliente → servidor): o livro pede a contagem dos ingredientes guardados por perto;</li>
 *   <li>{@link Nearby} (servidor → cliente): a soma por item dos móveis e estações num raio de {@link #NEARBY_RADIUS}.</li>
 * </ul>
 * A contagem é só informativa: nenhuma mensagem do cliente mexe em inventário.
 */
public final class ModNetwork {
    public static final int NEARBY_RADIUS = 8;
    private static final int PROTOCOL = 1;

    public record Catalog(List<RecipeCatalog.Entry> entries) {
        static final StreamCodec<RegistryFriendlyByteBuf, RecipeCatalog.Entry> ENTRY = StreamCodec.composite(
            Identifier.STREAM_CODEC, RecipeCatalog.Entry::id,
            CookingRecipe.STREAM_CODEC, RecipeCatalog.Entry::recipe,
            RecipeCatalog.Entry::new);
        static final StreamCodec<RegistryFriendlyByteBuf, Catalog> CODEC =
            ENTRY.apply(ByteBufCodecs.list()).map(Catalog::new, Catalog::entries);
    }

    public record NearbyRequest() {
        static final StreamCodec<RegistryFriendlyByteBuf, NearbyRequest> CODEC = StreamCodec.unit(new NearbyRequest());
    }

    public record Nearby(Map<Item, Integer> counts) {
        static final StreamCodec<RegistryFriendlyByteBuf, Nearby> CODEC =
            ByteBufCodecs.<RegistryFriendlyByteBuf, Item, Integer, Map<Item, Integer>>map(HashMap::new, ByteBufCodecs.registry(Registries.ITEM), ByteBufCodecs.VAR_INT)
                .map(Nearby::new, Nearby::counts);
    }

    public static final SimpleChannel CHANNEL = ChannelBuilder.named(Culinaria.id("main"))
        .networkProtocolVersion(PROTOCOL)
        .simpleChannel()
        .messageBuilder(Catalog.class, NetworkDirection.PLAY_TO_CLIENT)
            .codec(Catalog.CODEC)
            .consumerMainThread((message, context) -> RecipeCatalog.setClient(new RecipeCatalog(message.entries())))
            .add()
        .messageBuilder(NearbyRequest.class, NetworkDirection.PLAY_TO_SERVER)
            .codec(NearbyRequest.CODEC)
            .consumerMainThread((message, context) -> {
                ServerPlayer player = context.getSender();
                if (player != null) {
                    send(player, new Nearby(countNearby(player)));
                }
            })
            .add()
        .messageBuilder(Nearby.class, NetworkDirection.PLAY_TO_CLIENT)
            .codec(Nearby.CODEC)
            .consumerMainThread((message, context) -> NearbyCounts.set(message.counts()))
            .add();

    private ModNetwork() {}

    /** Força a criação do canal na construção do mod, dos dois lados. */
    public static void init() {
        CHANNEL.getName();
    }

    public static void send(ServerPlayer player, Object message) {
        CHANNEL.send(message, PacketDistributor.PLAYER.with(player));
    }

    public static void sendToServer(Object message) {
        CHANNEL.send(message, PacketDistributor.SERVER.noArg());
    }

    public static void sendCatalog(ServerPlayer player) {
        send(player, new Catalog(RecipeCatalog.server(player.level().getServer()).entries()));
    }

    /** Soma por item do que está guardado em móveis e estações do Culinária perto do jogador (só leitura). */
    public static Map<Item, Integer> countNearby(ServerPlayer player) {
        Map<Item, Integer> counts = new HashMap<>();
        ServerLevel level = player.level();
        BlockPos center = player.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-NEARBY_RADIUS, -3, -NEARBY_RADIUS), center.offset(NEARBY_RADIUS, 3, NEARBY_RADIUS))) {
            if (!level.isLoaded(pos)) {
                continue;
            }
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof StorageBlockEntity || entity instanceof CookingStationBlockEntity) {
                Container container = (Container) entity;
                for (int slot = 0; slot < container.getContainerSize(); slot++) {
                    ItemStack stack = container.getItem(slot);
                    if (!stack.isEmpty()) {
                        counts.merge(stack.getItem(), stack.getCount(), Integer::sum);
                    }
                }
            }
        }
        return counts;
    }
}
