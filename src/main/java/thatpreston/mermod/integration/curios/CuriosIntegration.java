package thatpreston.mermod.integration.curios;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
    public static ItemStack getNecklace(Player player) {
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        CuriosApi.getCuriosHelper().findFirstCurio(player, RegistryHandler.SEA_NECKLACE.get()).ifPresent(result -> {
            stack.set(result.stack());
        });
        return stack.get();
    }
    public static void registerSlotType() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
    }
    public static ICapabilityProvider getCapabilityProvider(ItemStack stack) {
        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> lazyCurio = LazyOptional.of(() -> new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }
                @Override
                public void curioTick(SlotContext slotContext) {
                    SeaNecklaceUtils.giveNecklaceEffects(slotContext.entity());
                }
            });
            @Override
            public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
                return CuriosCapability.ITEM.orEmpty(capability, lazyCurio);
            }
        };
    }
}