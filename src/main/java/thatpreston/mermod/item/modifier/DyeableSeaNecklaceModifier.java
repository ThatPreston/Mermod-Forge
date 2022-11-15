package thatpreston.mermod.item.modifier;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyeableSeaNecklaceModifier extends SeaNecklaceModifier implements DyeableLeatherItem {
    public DyeableSeaNecklaceModifier(NecklaceModifiers type) {
        super(type);
    }
    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compound = stack.getTagElement("display");
        return compound != null && compound.contains("color", 99) ? compound.getInt("color") : 16777215;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(new TranslatableComponent("tooltip.mermod.canBeDyed").withStyle(ChatFormatting.GRAY));
    }
}