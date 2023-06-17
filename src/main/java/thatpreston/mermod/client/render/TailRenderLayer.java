package thatpreston.mermod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import thatpreston.mermod.Mermod;

@OnlyIn(Dist.CLIENT)
public class TailRenderLayer<T extends PlayerEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    private TailModel model;
    public TailRenderLayer(IEntityRenderer<T, M> parent) {
        super(parent);
        this.model = new TailModel();
    }
    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float yaw, float pitch) {
        if(Mermod.checkTailConditions(entity)) {
            MermaidTailStyle style = Mermod.getTailStyle(entity);
            if(style != null) {
                stack.pushPose();
                this.model.updatePose(entity, this.getParentModel(), animationProgress);
                this.model.renderTail(stack, buffer, light, OverlayTexture.NO_OVERLAY, style);
                stack.popPose();
            }
        }
    }
}