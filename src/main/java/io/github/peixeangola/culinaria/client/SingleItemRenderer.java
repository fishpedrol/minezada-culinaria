package io.github.peixeangola.culinaria.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.peixeangola.culinaria.block.FurnitureBlock;
import io.github.peixeangola.culinaria.block.SingleItemBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Desenha o ingrediente posto na tábua de corte (deitado) ou no moedor (em pé, na boca do moedor), como a fogueira
 * vanilla desenha a comida. Só no cliente, a partir da pilha sincronizada pela BlockEntity.
 */
public class SingleItemRenderer<T extends SingleItemBlockEntity> implements BlockEntityRenderer<T, SingleItemRenderer.State> {
    private final ItemModelResolver itemModelResolver;
    private final boolean lying;
    private final float height;

    public SingleItemRenderer(BlockEntityRendererProvider.Context context, boolean lying, float height) {
        this.itemModelResolver = context.itemModelResolver();
        this.lying = lying;
        this.height = height;
    }

    public static final class State extends BlockEntityRenderState {
        final ItemStackRenderState item = new ItemStackRenderState();
        Direction facing = Direction.NORTH;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(T entity, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTicks, cameraPosition, breakProgress);
        state.facing = entity.getBlockState().getValue(FurnitureBlock.FACING);
        itemModelResolver.updateForTopItem(state.item, entity.item(), ItemDisplayContext.FIXED, entity.getLevel(), null,
            (int) entity.getBlockPos().asLong());
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        if (state.item.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5F, height, 0.5F);
        poseStack.rotateDegrees(Axis.YP, -state.facing.toYRot());
        if (lying) {
            poseStack.rotateDegrees(Axis.XP, 90.0F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(0.35F, 0.35F, 0.35F);
        }
        state.item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
