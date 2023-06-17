package thatpreston.mermod.integration.origins;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import thatpreston.mermod.client.render.MermaidTailStyle;

import java.util.List;

public class OriginsIntegration {
    private static final ResourceLocation FACTORY_IDENTIFIER = new ResourceLocation("mermod", "tail_style");
    public static boolean hasTailPower(PlayerEntity player) {
        return OriginComponent.hasPower(player, TailStylePower.class);
    }
    public static MermaidTailStyle getTailStyle(PlayerEntity player) {
        List<TailStylePower> powers = OriginComponent.getPowers(player, TailStylePower.class);
        if(powers != null && powers.size() > 0) {
            TailStylePower power = powers.get(0);
            return new MermaidTailStyle(power.getTailColor(), power.getHasBra(), power.getBraColor(), power.getHasGradient(), power.getGradientColor(), power.getHasGlint(), power.getTexture());
        }
        return null;
    }
    public static void registerPowerFactory() {
        SerializableData serializableData = new SerializableData();
        serializableData.add("tailColor", SerializableDataType.INT, 16777215);
        serializableData.add("hasBra", SerializableDataType.BOOLEAN, false);
        serializableData.add("braColor", SerializableDataType.INT, 16777215);
        serializableData.add("hasGradient", SerializableDataType.BOOLEAN, false);
        serializableData.add("gradientColor", SerializableDataType.INT, 16777215);
        serializableData.add("hasGlint", SerializableDataType.BOOLEAN, false);
        serializableData.add("texture", SerializableDataType.IDENTIFIER, MermaidTailStyle.DEFAULT_TEXTURE);
        PowerFactory serializer = new PowerFactory<>(FACTORY_IDENTIFIER, serializableData, data -> (type, entity) -> {
            return new TailStylePower(type, entity, data.getInt("tailColor"), data.getBoolean("hasBra"), data.getInt("braColor"), data.getBoolean("hasGradient"), data.getInt("gradientColor"), data.getBoolean("hasGlint"), data.getId("texture"));
        }).allowCondition();
        Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}