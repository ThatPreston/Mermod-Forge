package thatpreston.mermod.client.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thatpreston.mermod.Config;

@OnlyIn(Dist.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void onGetNightVisionScale(LivingEntity entity, float f, CallbackInfoReturnable<Float> info) {
        if(Config.CLIENT.nightVisionFlashingFix.get() && entity instanceof PlayerEntity && entity.hasEffect(Effects.NIGHT_VISION)) {
            info.setReturnValue(1.0F);
        }
    }
}