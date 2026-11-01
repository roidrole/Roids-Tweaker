package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import roidrole.roidtweaker.utils.predicates.PredicateAnyOfArray;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.buildcraft.IntegrationTable")
@ZenRegister
@ModOnly("buildcraftsilicon")
@SuppressWarnings("unused")
public class IntegrationTable {
	@ZenMethod
	public static void add(IItemStack output, long energyCost, IIngredient catalyst, IIngredient[] inputs) {
		ItemStack out = CraftTweakerMC.getItemStack(output);
		List<IngredientStack> in = BuildUtil.getIngredients(inputs);
		IngredientStack c = new IngredientStack(CraftTweakerMC.getIngredient(catalyst));
		final IntegrationRecipe recipe = new IntegrationRecipeBasic(Utils.newRL(), energyCost * MjAPI.MJ, c, in, out);
		BuildcraftRecipeRegistry.integrationRecipes.addRecipe(recipe);
	}

	@ZenMethod
	public static void remove(IIngredient catalyst) {
		final Predicate<ItemStack> catalystFilter = Utils.getStackMatcher(catalyst);

		ListRemove.remove(
			BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes(),
			recipe -> PredicateAnyOfArray.test(catalystFilter, recipe.getCenterStack().ingredient.matchingStacks)
		);
	}

	@ZenMethod
	public static void removeAll() {
		Iterable<IntegrationRecipe> iterable = BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes();
		if(iterable instanceof Collection){
			ListClear.clear((Collection<?>) iterable, "Integration Table");
		} else {
			ListClear.clear(iterable, "Integration Table");
		}
	}
}
