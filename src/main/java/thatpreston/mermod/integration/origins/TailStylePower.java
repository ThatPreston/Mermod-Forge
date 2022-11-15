package thatpreston.mermod.integration.origins;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.calio.api.network.CalioCodecHelper;
import net.minecraft.resources.ResourceLocation;
import thatpreston.mermod.client.render.MermaidTailStyle;

public class TailStylePower extends PowerFactory<TailStyleConfiguration> {
    public static final Codec<TailStyleConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CalioCodecHelper.INT.optionalFieldOf("tailColor", 16777215).forGetter(TailStyleConfiguration::tailColor),
            CalioCodecHelper.BOOL.optionalFieldOf("hasBra", false).forGetter(TailStyleConfiguration::hasBra),
            CalioCodecHelper.INT.optionalFieldOf("braColor", 16777215).forGetter(TailStyleConfiguration::braColor),
            CalioCodecHelper.BOOL.optionalFieldOf("hasGradient", false).forGetter(TailStyleConfiguration::hasGradient),
            CalioCodecHelper.INT.optionalFieldOf("gradientColor", 16777215).forGetter(TailStyleConfiguration::gradientColor),
            CalioCodecHelper.BOOL.optionalFieldOf("hasGlint", false).forGetter(TailStyleConfiguration::hasGlint),
            ResourceLocation.CODEC.optionalFieldOf("texture", MermaidTailStyle.DEFAULT_TEXTURE).forGetter(TailStyleConfiguration::texture)
    ).apply(instance, TailStyleConfiguration::new));
    public TailStylePower() {
        super(CODEC);
    }
}