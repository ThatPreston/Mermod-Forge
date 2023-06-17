package thatpreston.mermod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import thatpreston.mermod.client.render.TailRenderLayer;

import java.util.Map;

@Mod.EventBusSubscriber(modid = "mermod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MermodClient {
    @SubscribeEvent
    public static void clientInit(final FMLClientSetupEvent event) {
        Minecraft.getInstance().getItemColors().register((stack, index) -> index > 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1, RegistryHandler.SEA_NECKLACE.get());
        Minecraft.getInstance().getItemColors().register((stack, index) -> ((IDyeableArmorItem)stack.getItem()).getColor(stack), RegistryHandler.MERMAID_BRA_MODIFIER.get(), RegistryHandler.TAIL_GRADIENT_MODIFIER.get());
        addLayer();
    }
    private static void addLayer() {
        Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        for(PlayerRenderer renderer : skinMap.values()) {
            renderer.addLayer(new TailRenderLayer(renderer));
        }
    }
}