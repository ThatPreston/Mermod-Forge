package thatpreston.mermod.client.mixin;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.client.render.MermaidTailStyle;
import thatpreston.mermod.client.render.TailRenderLayer;

@OnlyIn(Dist.CLIENT)
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V", at = @At("TAIL"))
    public void onConstructor(EntityRendererProvider.Context context, boolean slim, CallbackInfo info) {
        this.addLayer(new TailRenderLayer(this, context.getModelSet()));
    }
    @Inject(method = "setModelProperties", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isCrouching()Z", shift = At.Shift.AFTER))
    public void onSetModelProperties(AbstractClientPlayer player, CallbackInfo info) {
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