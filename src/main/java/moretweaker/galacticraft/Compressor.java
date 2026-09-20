package moretweaker.galacticraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.recipes.MCRecipeShaped;
import crafttweaker.mc1120.recipes.MCRecipeShapeless;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import roidrole.roidtweaker.mixins.galacticraft.AccCompressorRecipes;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.galacticraft.Compressor")
@ZenRegister
@ModOnly("galacticraftcore")
@SuppressWarnings("unused")
public class Compressor {
	@ZenMethod
	public static void addShaped(IItemStack output, IItemStack[][] inputs) {
		final IRecipe recipe = new MCRecipeShaped(inputs, output, null, null, false, false);
		AccCompressorRecipes.getRecipes().add(recipe);
	}

	@ZenMethod
	public static void addShapeless(IItemStack output, IIngredient[] inputs) {
		final IRecipe recipe = new MCRecipeShapeless(inputs, output, null, null, false);
		AccCompressorRecipes.getRecipes().add(recipe);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		if (!out.isEmpty()) {
			final Object[] in = Utils.getObjects(inputs, Utils::getObjectOreStack);
			CompressorRecipes.addShapelessRecipe(out, in);
		}
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		AccCompressorRecipes.getRecipes().removeIf(recipe -> outFilter.test(recipe.getRecipeOutput()));
		AccCompressorRecipes.getRecipesAdventure().removeIf(recipe -> outFilter.test(recipe.getRecipeOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		AccCompressorRecipes.getRecipes().clear();
		AccCompressorRecipes.getRecipesAdventure().clear();
	}
}
