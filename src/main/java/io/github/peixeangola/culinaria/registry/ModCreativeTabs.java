package io.github.peixeangola.culinaria.registry;

import io.github.peixeangola.culinaria.Culinaria;
import io.github.peixeangola.culinaria.content.GeneratedContent;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/** "Culinária" (cozinha, utensílios, sementes, mudas e ingredientes) e "Culinária: pratos" (os 100 pratos). */
public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Culinaria.MOD_ID);

    public static final RegistryObject<CreativeModeTab> KITCHEN = CREATIVE_TABS.register("kitchen", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.culinaria"))
        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
        .icon(() -> ModItems.item("stove").getDefaultInstance())
        .displayItems((parameters, output) -> {
            GeneratedContent.FURNITURE.forEach(f -> output.accept(ModItems.item(f.id())));
            GeneratedContent.ITEMS.stream().filter(i -> i.kind() == GeneratedContent.Kind.TOOL).forEach(i -> output.accept(ModItems.item(i.id())));
            GeneratedContent.CROPS.forEach(c -> output.accept(ModItems.item(c.seeds())));
            GeneratedContent.TREES.forEach(t -> {
                output.accept(ModItems.item(t.sapling()));
                output.accept(ModItems.item(t.leaves()));
            });
            GeneratedContent.ITEMS.stream().filter(i -> i.kind() == GeneratedContent.Kind.INGREDIENT).forEach(i -> output.accept(ModItems.item(i.id())));
        })
        .build());

    public static final RegistryObject<CreativeModeTab> DISHES = CREATIVE_TABS.register("dishes", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.culinaria.dishes"))
        .withTabsBefore(KITCHEN.getKey())
        .icon(() -> ModItems.item("feijoada").getDefaultInstance())
        .displayItems((parameters, output) ->
            GeneratedContent.ITEMS.stream().filter(i -> i.kind() == GeneratedContent.Kind.DISH).forEach(i -> output.accept(ModItems.item(i.id()))))
        .build());

    private ModCreativeTabs() {}
}
