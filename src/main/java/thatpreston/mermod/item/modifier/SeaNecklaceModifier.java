package thatpreston.mermod.item.modifier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import thatpreston.mermod.RegistryHandler;

public class SeaNecklaceModifier extends Item {
    private final NecklaceModifiers modifierType;
    public SeaNecklaceModifier(NecklaceModifiers type) {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
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