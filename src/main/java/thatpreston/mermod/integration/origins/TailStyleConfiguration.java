package thatpreston.mermod.integration.origins;

import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import net.minecraft.resources.ResourceLocation;

public record TailStyleConfiguration(int tailColor, boolean hasBra, int braColor, boolean hasGradient, int gradientColor, boolean hasGlint, ResourceLocation texture) implements IDynamicFeatureConfiguration {}