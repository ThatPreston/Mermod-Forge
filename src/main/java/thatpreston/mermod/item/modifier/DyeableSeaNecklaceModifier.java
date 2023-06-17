package thatpreston.mermod.item.modifier;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class DyeableSeaNecklaceModifier extends SeaNecklaceModifier implements IDyeableArmorItem {
    public DyeableSeaNecklaceModifier(NecklaceModifiers type) {
        super(type);
    }
    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compound = stack.getTagElement("display");
        return compound != null && compound.contains("color", 99) ? compound.getInt("color") : 16777215;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        tooltip.add(new TranslationTextComponent("tooltip.mermod.canBeDyed").withStyle(TextFormatting.GRAY));
    }
}