package thatpreston.mermod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import thatpreston.mermod.item.modifier.DyeableSeaNecklaceModifier;
import thatpreston.mermod.item.SeaNecklace;
import thatpreston.mermod.item.modifier.NecklaceModifiers;
import thatpreston.mermod.item.modifier.SeaNecklaceModifier;
import thatpreston.mermod.recipe.NecklaceModifierRecipe;

import java.util.ArrayList;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "mermod");
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "mermod");
    public static final RegistryObject<Item>
            SEA_NECKLACE = ITEMS.register("sea_necklace", SeaNecklace::new),
            SEA_CRYSTAL = ITEMS.register("sea_crystal", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS))),
            MERMAID_BRA_MODIFIER = ITEMS.register("mermaid_bra_modifier", () -> new DyeableSeaNecklaceModifier(NecklaceModifiers.MERMAID_BRA)),
            TAIL_GRADIENT_MODIFIER = ITEMS.register("tail_gradient_modifier", () -> new DyeableSeaNecklaceModifier(NecklaceModifiers.TAIL_GRADIENT)),
            GLOWING_PEARL_MODIFIER = ITEMS.register("glowing_pearl_modifier", () -> new SeaNecklaceModifier(NecklaceModifiers.GLOWING_PEARL)),
            MOON_ROCK_MODIFIER = ITEMS.register("moon_rock_modifier", () -> new SeaNecklaceModifier(NecklaceModifiers.MOON_ROCK)),
            URSULA_SHELL_MODIFIER = ITEMS.register("ursula_shell_modifier", () -> new SeaNecklaceModifier(NecklaceModifiers.URSULA_SHELL));
    public static RegistryObject<SimpleRecipeSerializer<NecklaceModifierRecipe>> NECKLACE_MODIFIER_SERIALIZER = RECIPES.register("crafting_special_necklace_modifier", () -> new SimpleRecipeSerializer<>(NecklaceModifierRecipe::new));
    public static ArrayList<SeaNecklaceModifier> NECKLACE_MODIFIERS = new ArrayList<>();
}