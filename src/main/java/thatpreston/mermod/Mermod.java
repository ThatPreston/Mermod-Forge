package thatpreston.mermod;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import thatpreston.mermod.integration.curios.CuriosIntegration;
import thatpreston.mermod.item.SeaNecklace;
import thatpreston.mermod.client.render.MermaidTailStyle;
import thatpreston.mermod.item.modifier.SeaNecklaceModifier;

@Mod("mermod")
public class Mermod {
    public static boolean curiosInstalled = false;
    public Mermod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonInit);
        bus.addListener(this::buildCreativeTabs);
        bus.addListener(this::sendIMC);
        MinecraftForge.EVENT_BUS.register(this);
        RegistryHandler.ITEMS.register(bus);
        RegistryHandler.RECIPES.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        curiosInstalled = ModList.get().isLoaded("curios");
    }
    private void commonInit(final FMLCommonSetupEvent event) {
        CauldronInteraction.WATER.put(RegistryHandler.SEA_NECKLACE.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(RegistryHandler.MERMAID_BRA_MODIFIER.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(RegistryHandler.TAIL_GRADIENT_MODIFIER.get(), CauldronInteraction.DYED_ITEM);
    }
    private void buildCreativeTabs(final CreativeModeTabEvent.BuildContents event) {
        CreativeModeTab tab = event.getTab();
        if(tab.equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(RegistryHandler.SEA_CRYSTAL.get());
            for(SeaNecklaceModifier modifier : RegistryHandler.NECKLACE_MODIFIERS) {
                event.accept(modifier);
            }
        } else if(tab.equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(RegistryHandler.SEA_NECKLACE.get());
        }
    }
    private void sendIMC(final InterModEnqueueEvent event) {
        if(curiosInstalled) {
            CuriosIntegration.registerSlotType();
        }
    }
    public static ItemStack getNecklace(Player player) {
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
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
    public static boolean getPlayerHasTail(Player player) {
        ItemStack necklace = getNecklace(player);
        return !necklace.isEmpty();
    }
    public static MermaidTailStyle getTailStyle(Player player) {
        ItemStack necklace = getNecklace(player);
        if(!necklace.isEmpty()) {
            return new MermaidTailStyle(necklace);
        }
        return null;
    }
}