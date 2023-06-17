package thatpreston.mermod.recipe;

import com.google.common.collect.Lists;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thatpreston.mermod.utils.SeaNecklaceUtils;
import thatpreston.mermod.RegistryHandler;
import thatpreston.mermod.item.ISeaNecklace;
import thatpreston.mermod.item.modifier.SeaNecklaceModifier;

import java.util.List;

public class NecklaceModifierRecipe extends SpecialRecipe {
    public NecklaceModifierRecipe(ResourceLocation location) {
        super(location);
    }
    @Override
    public boolean matches(CraftingInventory container, World world) {
        ItemStack necklace = ItemStack.EMPTY;
        List<ItemStack> modifiers = Lists.newArrayList();
        for(int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ISeaNecklace) {
                    if(!necklace.isEmpty()) {
                        return false;
                    }
                    necklace = stack;
                } else {
                    if(!(stack.getItem() instanceof SeaNecklaceModifier)) {
                        return false;
                    }
                    modifiers.add(stack);
                }
            }
        }
        return !necklace.isEmpty() && !modifiers.isEmpty() && SeaNecklaceUtils.canAddModifiers(necklace, modifiers);
    }
    @Override
    public ItemStack assemble(CraftingInventory container) {
        List<ItemStack> modifiers = Lists.newArrayList();
        ItemStack necklace = ItemStack.EMPTY;
        for(int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if(!stack.isEmpty()) {
                if(stack.getItem() instanceof ISeaNecklace) {
                    if(!necklace.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    necklace = stack.copy();
                } else {
                    if(!(stack.getItem() instanceof SeaNecklaceModifier)) {
                        return ItemStack.EMPTY;
                    }
                    modifiers.add(stack);
                }
            }
        }
        if(!necklace.isEmpty() && !modifiers.isEmpty()) {
            SeaNecklaceUtils.addModifiers(necklace, modifiers);
            return necklace;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
    @Override
    public SpecialRecipeSerializer<?> getSerializer() {
        return RegistryHandler.NECKLACE_MODIFIER_SERIALIZER.get();
    }
}