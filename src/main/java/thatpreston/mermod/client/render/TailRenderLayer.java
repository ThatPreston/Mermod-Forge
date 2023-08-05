package thatpreston.mermod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.MermodClient;

@OnlyIn(Dist.CLIENT)
public class TailRenderLayer<T extends Player, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private TailModel model;
    public TailRenderLayer(RenderLayerParent<T, M> parent, EntityModelSet set) {
        super(parent);
        this.model = new TailModel(set.bakeLayer(MermodClient.TAIL));
    }
    @Override
    public void render(PoseStack stack, MultiBufferSource source, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float yaw, float pitch) {
        if(Mermod.shouldRenderTail(entity)) {
            MermaidTailStyle style = Mermod.getTailStyle(entity);
            if(style != null) {
                stack.pushPose();
                this.model.updatePose(entity, this.getParentModel(), animationProgress);
                this.model.renderTail(stack, source, light, OverlayTexture.NO_OVERLAY, style);
                stack.popPose();
            }
        }
    }
}