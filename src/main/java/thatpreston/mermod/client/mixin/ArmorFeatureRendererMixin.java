package thatpreston.mermod.client.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.client.render.MermaidTailStyle;

import static net.minecraft.inventory.EquipmentSlotType.*;

@OnlyIn(Dist.CLIENT)
@Mixin(BipedArmorLayer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
    public ArmorFeatureRendererMixin(IEntityRenderer<T, M> context) {
        super(context);
    }
    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void onRenderArmorPiece(MatrixStack stack, IRenderTypeBuffer source, T entity, EquipmentSlotType slot, int i, A model, CallbackInfo info) {
        if(entity instanceof PlayerEntity && Mermod.checkTailConditions(entity)) {
            MermaidTailStyle style = Mermod.getTailStyle((PlayerEntity)entity);
            if(style != null) {
                boolean hasBra = style.getHasBra();
                if(slot == LEGS || slot == FEET || (hasBra && slot == CHEST)) {
                    info.cancel();
                }
            }
        }
    }
}