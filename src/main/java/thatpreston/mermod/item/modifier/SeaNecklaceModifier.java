package thatpreston.mermod.item.modifier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import thatpreston.mermod.RegistryHandler;

public class SeaNecklaceModifier extends Item {
    private final NecklaceModifiers modifierType;
    public SeaNecklaceModifier(NecklaceModifiers type) {
        super(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1));
        this.modifierType = type;
        RegistryHandler.NECKLACE_MODIFIERS.add(this);
    }
    public NecklaceModifiers getModifierType() {
        return modifierType;
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return modifierType.getHasGlint();
    }
}