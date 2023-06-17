package thatpreston.mermod.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.*;
import thatpreston.mermod.Config;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.RegistryHandler;
import thatpreston.mermod.item.modifier.NecklaceModifiers;
import thatpreston.mermod.item.modifier.SeaNecklaceModifier;

import java.util.ArrayList;
import java.util.List;

public class SeaNecklaceUtils {
    public static void addModifier(ItemStack necklace, ItemStack stack) {
        CompoundNBT compound = necklace.getOrCreateTagElement("necklace_modifiers");
        SeaNecklaceModifier modifier = (SeaNecklaceModifier)stack.getItem();
        String id = modifier.getModifierType().getId();
        String category = modifier.getModifierType().getCategory();
        compound.putString(category, id);
        if(modifier instanceof IDyeableArmorItem) {
            compound.putInt(category + "_color", ((IDyeableArmorItem)modifier).getColor(stack));
        }
    }
    public static ItemStack removeModifier(ItemStack necklace, SeaNecklaceModifier modifier) {
        CompoundNBT compound = necklace.getOrCreateTagElement("necklace_modifiers");
        NecklaceModifiers modifierType = modifier.getModifierType();
        String id = modifierType.getId();
        String category = modifierType.getCategory();
        if(compound.getString(category).equals(id)) {
            ItemStack stack = new ItemStack(modifier);
            compound.remove(category);
            if(modifierType.isColorable()) {
                stack.getOrCreateTagElement("display").putInt("color", compound.getInt(category + "_color"));
                compound.remove(category + "_color");
            }
            return stack;
        }
        return null;
    }
    public static ItemStack removeModifier(ItemStack necklace) {
        for(int i = 0; i < RegistryHandler.NECKLACE_MODIFIERS.size(); i++) {
            SeaNecklaceModifier modifier = RegistryHandler.NECKLACE_MODIFIERS.get(i);
            ItemStack stack = removeModifier(necklace, modifier);
            if(stack != null) {
                return stack;
            }
        }
        return null;
    }
    public static boolean hasModifier(ItemStack necklace, NecklaceModifiers modifierType) {
        CompoundNBT compound = necklace.getOrCreateTagElement("necklace_modifiers");
        return compound.contains(modifierType.getCategory());
    }
    public static boolean canAddModifiers(ItemStack necklace, List<ItemStack> modifiers) {
        ArrayList<String> presentModifiers = new ArrayList<>();
        for(int i = 0; i < modifiers.size(); i++) {
            SeaNecklaceModifier modifier = (SeaNecklaceModifier)modifiers.get(i).getItem();
            String category = modifier.getModifierType().getCategory();
            if(hasModifier(necklace, modifier.getModifierType()) || presentModifiers.contains(category)) {
                return false;
            }
            presentModifiers.add(category);
        }
        return true;
    }
    public static void addModifiers(ItemStack necklace, List<ItemStack> modifiers) {
        for(int i = 0; i < modifiers.size(); i++) {
            addModifier(necklace, modifiers.get(i));
        }
    }
    public static void appendHoverText(ItemStack necklace, List<ITextComponent> list) {
        CompoundNBT compound = necklace.getOrCreateTagElement("necklace_modifiers");
        int count = 0;
        for(NecklaceModifiers modifierType : NecklaceModifiers.values()) {
            String id = modifierType.getId();
            String category = modifierType.getCategory();
            if(compound.getString(category).equals(id)) {
                TranslationTextComponent text = new TranslationTextComponent("item.mermod." + id + "_modifier");
                if(modifierType.isColorable()) {
                    text.setStyle(Style.EMPTY.withColor(Color.fromRgb(compound.getInt(category + "_color"))));
                } else {
                    text.setStyle(Style.EMPTY.withColor(Color.fromRgb(modifierType.getTooltipColor())));
                }
                list.add(text);
                count++;
            }
        }
        if(count > 0) {
            list.add(new TranslationTextComponent("tooltip.mermod.modifierHint").withStyle(TextFormatting.GRAY));
        }
    }
    public static void giveNecklaceEffects(LivingEntity entity) {
        if(Mermod.checkTailConditions(entity)) {
            if(Config.SERVER.waterBreathing.get() && !entity.hasEffect(Effects.WATER_BREATHING)) {
                entity.addEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0, false, false));
            }
            if(Config.SERVER.nightVision.get() && !entity.hasEffect(Effects.NIGHT_VISION)) {
                entity.addEffect(new EffectInstance(Effects.NIGHT_VISION, 200, 0, false, false));
            }
        }
    }
}