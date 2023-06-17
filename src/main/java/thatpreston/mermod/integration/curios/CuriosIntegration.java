package thatpreston.mermod.integration.curios;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import thatpreston.mermod.RegistryHandler;
import thatpreston.mermod.utils.SeaNecklaceUtils;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.concurrent.atomic.AtomicReference;

public class CuriosIntegration {
    public static ItemStack getNecklace(PlayerEntity player) {
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.SEA_NECKLACE.get(), player).ifPresent(result -> stack.set(result.getRight()));
        return stack.get();
    }
    public static void registerSlotType() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
    }
    public static ICapabilityProvider getCapabilityProvider(ItemStack stack) {
        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> lazyCurio = LazyOptional.of(() -> new ICurio() {
                @Override
                public void curioTick(String identifier, int index, LivingEntity entity) {
                    SeaNecklaceUtils.giveNecklaceEffects(entity);
                }
            });
            @Override
            public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
                return CuriosCapability.ITEM.orEmpty(capability, lazyCurio);
            }
        };
    }
}