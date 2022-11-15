package thatpreston.mermod.integration.origins;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import thatpreston.mermod.client.render.MermaidTailStyle;

import java.util.List;

public class OriginsIntegration {
    public static final DeferredRegister<PowerFactory<?>> POWER_REGISTRY = DeferredRegister.create(ApoliRegistries.POWER_FACTORY_KEY, "mermod");
    public static final RegistryObject<TailStylePower> TAIL_STYLE = POWER_REGISTRY.register("tail_style", () -> new TailStylePower());
    public static boolean hasTailPower(Player player) {
        return IPowerContainer.hasPower(player, TAIL_STYLE.get());
    }
    public static MermaidTailStyle getTailStyle(Player player) {
        List<ConfiguredPower<TailStyleConfiguration, PowerFactory<TailStyleConfiguration>>> powers = IPowerContainer.getPowers(player, TAIL_STYLE.get());
        if(powers != null && powers.size() > 0) {
            TailStyleConfiguration configuration = powers.get(0).getConfiguration();
            return new MermaidTailStyle(configuration.tailColor(), configuration.hasBra(), configuration.braColor(), configuration.hasGradient(), configuration.gradientColor(), configuration.hasGlint(), configuration.texture());
        }
        return null;
    }
    public static void register(IEventBus bus) {
        POWER_REGISTRY.register(bus);
    }
}