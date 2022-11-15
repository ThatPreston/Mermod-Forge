package thatpreston.mermod.client.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.Config;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(method = "travel", at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    public float modifyFloat(float original) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if(Config.SERVER.swimSpeed.get() && Mermod.checkTailConditions(entity) && entity instanceof Player && Mermod.getPlayerHasTail((Player)entity)) {
            double multiplier = Config.SERVER.swimSpeedMultiplier.get();
            return original * (float)multiplier;
        }
        return original;
    }
}