package thatpreston.mermod.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.client.render.MermaidTailStyle;

import static net.minecraft.world.entity.EquipmentSlot.*;

@OnlyIn(Dist.CLIENT)
@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public ArmorFeatureRendererMixin(RenderLayerParent<T, M> context) {
        super(context);
    }
    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void onRenderArmorPiece(PoseStack stack, MultiBufferSource source, T entity, EquipmentSlot slot, int i, A model, CallbackInfo info) {
        if(entity instanceof Player && Mermod.shouldRenderTail(entity)) {
            MermaidTailStyle style = Mermod.getTailStyle((Player)entity);
            if(style != null) {
                boolean hasBra = style.getHasBra();
                if(slot == LEGS || slot == FEET || (hasBra && slot == CHEST)) {
                    info.cancel();
                }
            }
        }
    }
}