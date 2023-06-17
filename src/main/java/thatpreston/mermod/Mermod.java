package thatpreston.mermod;

import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import thatpreston.mermod.integration.curios.CuriosIntegration;
import thatpreston.mermod.integration.origins.OriginsIntegration;
import thatpreston.mermod.item.SeaNecklace;
import thatpreston.mermod.client.render.MermaidTailStyle;

@Mod("mermod")
public class Mermod {
    public static boolean curiosInstalled = false;
    public static boolean originsInstalled = false;
    public Mermod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::sendIMC);
        MinecraftForge.EVENT_BUS.register(this);
        RegistryHandler.ITEMS.register(bus);
        RegistryHandler.RECIPES.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        curiosInstalled = ModList.get().isLoaded("curios");
        originsInstalled = ModList.get().isLoaded("origins");
        if(originsInstalled) {
            EventBuses.registerModEventBus("mermod", bus);
            OriginsIntegration.registerPowerFactory();
        }
    }
    private void sendIMC(final InterModEnqueueEvent event) {
        if(curiosInstalled) {
            CuriosIntegration.registerSlotType();
        }
    }
    public static ItemStack getNecklace(PlayerEntity player) {
        ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
        if(!chest.isEmpty() && chest.getItem() instanceof SeaNecklace) {
            return chest;
        } else if(curiosInstalled) {
            return CuriosIntegration.getNecklace(player);
        }
        return ItemStack.EMPTY;
    }
    public static boolean checkTailConditions(Entity entity) {
        return !entity.isInvisible() && entity.isInWater();
    }
    public static boolean getPlayerHasTail(PlayerEntity player) {
        ItemStack necklace = getNecklace(player);
        if(necklace.isEmpty() && originsInstalled) {
            return OriginsIntegration.hasTailPower(player);
        }
        return !necklace.isEmpty();
    }
    public static MermaidTailStyle getTailStyle(PlayerEntity player) {
        ItemStack necklace = getNecklace(player);
        if(!necklace.isEmpty()) {
            return new MermaidTailStyle(necklace);
        } else if(originsInstalled) {
            return OriginsIntegration.getTailStyle(player);
        }
        return null;
    }
}