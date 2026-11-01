package moretweaker.galacticraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.recipes.MCRecipeShaped;
import crafttweaker.mc1120.recipes.MCRecipeShapeless;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.galacticraft.AccCompressorRecipes;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
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
		final RecipeGCRT recipe = new RecipeGCRT(
			new MCRecipeShaped(inputs, output, null, null, false, false)
		);
		ListAdd.add(AccCompressorRecipes.getRecipes(), recipe);
	}

	@ZenMethod
	public static void addShapeless(IItemStack output, IIngredient[] inputs) {
		final RecipeGCRT recipe = new RecipeGCRT(
			new MCRecipeShapeless(inputs, output, null, null, false)
		);
		ListAdd.add(AccCompressorRecipes.getRecipes(), recipe);
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		ListRemove.remove(AccCompressorRecipes.getRecipes(), recipe -> outFilter.test(recipe.getRecipeOutput()));
		ListRemove.remove(AccCompressorRecipes.getRecipesAdventure(), recipe -> outFilter.test(recipe.getRecipeOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		AccCompressorRecipes.getRecipes().clear();
		AccCompressorRecipes.getRecipesAdventure().clear();
		ListClear.logMessage("Galacticraft compressor");
	}
}
