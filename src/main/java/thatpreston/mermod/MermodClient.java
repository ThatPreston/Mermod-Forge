package thatpreston.mermod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import thatpreston.mermod.client.render.TailModel;
import thatpreston.mermod.client.render.TailRenderLayer;

@Mod.EventBusSubscriber(modid = "mermod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MermodClient {
    public static final ModelLayerLocation TAIL = new ModelLayerLocation(new ResourceLocation("mermod", "tail"), "tail");
    @SubscribeEvent
    public static void clientInit(final FMLClientSetupEvent event) {
        Minecraft.getInstance().getItemColors().register((stack, index) -> index > 0 ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1, RegistryHandler.SEA_NECKLACE.get());
        Minecraft.getInstance().getItemColors().register((stack, index) -> ((DyeableLeatherItem)stack.getItem()).getColor(stack), RegistryHandler.MERMAID_BRA_MODIFIER.get(), RegistryHandler.TAIL_GRADIENT_MODIFIER.get());
    }
    @SubscribeEvent
    public static void registerLayer(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TAIL, TailModel::getTexturedModelData);
    }
    @SubscribeEvent
    public static void addLayer(final EntityRenderersEvent.AddLayers event) {
        for(String skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer = event.getSkin(skin);
            if(renderer != null) {
                renderer.addLayer(new TailRenderLayer(renderer, event.getEntityModels()));
            }
        }
    }
}