package thatpreston.mermod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.client.ForgeHooksClient;
import thatpreston.mermod.client.render.TailModel;

public class MermodClient {
    public static final ModelLayerLocation TAIL = new ModelLayerLocation(new ResourceLocation("mermod", "tail"), "tail");
    public static void init() {
        Minecraft.getInstance().getItemColors().register((stack, index) -> index > 0 ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1, RegistryHandler.SEA_NECKLACE.get());
        Minecraft.getInstance().getItemColors().register((stack, index) -> ((DyeableLeatherItem)stack.getItem()).getColor(stack), RegistryHandler.MERMAID_BRA_MODIFIER.get(), RegistryHandler.TAIL_GRADIENT_MODIFIER.get());
        ForgeHooksClient.registerLayerDefinition(TAIL, () -> TailModel.getTexturedModelData());
    }
}