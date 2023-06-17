package thatpreston.mermod.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import thatpreston.mermod.Mermod;
import thatpreston.mermod.integration.curios.CuriosIntegration;
import thatpreston.mermod.utils.SeaNecklaceUtils;

import java.util.List;

public class SeaNecklace extends Item implements IDyeableArmorItem, ISeaNecklace {
    public SeaNecklace() {
        super(new Item.Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1));
    }
    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compound = stack.getTagElement("display");
        return compound != null && compound.contains("color", 99) ? compound.getInt("color") : 16777215;
    }
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack necklace = player.getItemInHand(hand);
        if(player.isCrouching()) {
            ItemStack stack = SeaNecklaceUtils.removeModifier(necklace);
            if(stack != null) {
                player.addItem(stack);
                return ActionResult.success(necklace);
            }
        }
        return super.use(world, player, hand);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            if(livingEntity.getItemBySlot(EquipmentSlotType.CHEST).equals(stack)) {
                SeaNecklaceUtils.giveNecklaceEffects(livingEntity);
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        SeaNecklaceUtils.appendHoverText(stack, list);
    }
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.CHEST;
    }
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT compound) {
        if(Mermod.curiosInstalled) {
            return CuriosIntegration.getCapabilityProvider(stack);
        }
        return null;
    }
}