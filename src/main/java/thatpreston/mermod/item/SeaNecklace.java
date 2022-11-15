package thatpreston.mermod.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.integration.curios.CuriosIntegration;
import thatpreston.mermod.utils.SeaNecklaceUtils;

import java.util.List;

public class SeaNecklace extends Item implements DyeableLeatherItem, ISeaNecklace {
    public SeaNecklace() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));
    }
    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compound = stack.getTagElement("display");
        return compound != null && compound.contains("color", 99) ? compound.getInt("color") : 16777215;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack necklace = player.getItemInHand(hand);
        if(player.isCrouching()) {
            ItemStack stack = SeaNecklaceUtils.removeModifier(necklace);
            if(stack != null) {
                player.addItem(stack);
                return InteractionResultHolder.success(necklace);
            }
        }
        return super.use(level, player, hand);
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if(entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            if(livingEntity.getItemBySlot(EquipmentSlot.CHEST).equals(stack)) {
                SeaNecklaceUtils.giveNecklaceEffects(livingEntity);
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        SeaNecklaceUtils.appendHoverText(stack, list);
    }
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag compound) {
        if(Mermod.curiosInstalled) {
            return CuriosIntegration.getCapabilityProvider(stack);
        }
        return null;
    }
}