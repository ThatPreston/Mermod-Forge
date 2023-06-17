package thatpreston.mermod.client.mixin;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.client.render.MermaidTailStyle;

@OnlyIn(Dist.CLIENT)
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
    public PlayerRendererMixin(EntityRendererManager manager, PlayerModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(manager, model, shadowRadius);
    }
    @Inject(method = "setModelProperties", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;isCrouching()Z", shift = At.Shift.AFTER))
    public void onSetModelProperties(AbstractClientPlayerEntity player, CallbackInfo info) {
        if(Mermod.checkTailConditions(player)) {
            MermaidTailStyle style = Mermod.getTailStyle(player);
            if (style != null) {
                this.getModel().rightLeg.visible = false;
                this.getModel().leftLeg.visible = false;
                this.getModel().rightPants.visible = false;
                this.getModel().leftPants.visible = false;
            }
        }
    }
}