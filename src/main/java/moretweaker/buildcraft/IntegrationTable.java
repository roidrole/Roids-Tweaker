package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
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
		final IntegrationRecipe recipe = new IntegrationRecipeBasic(MoreTweaker.newRL(), energyCost * MjAPI.MJ, c, in, out);
		BuildcraftRecipeRegistry.integrationRecipes.addRecipe(recipe);
	}

	@ZenMethod
	public static void remove(IIngredient catalyst) {
		final Predicate<ItemStack> catalystFilter = Utils.getStackMatcher(catalyst);

		Iterator<IntegrationRecipe> iterator = BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes().iterator();
		while (iterator.hasNext()){
			IntegrationRecipe recipe = iterator.next();
			if(Utils.anyMatch(catalystFilter, recipe.getCenterStack().ingredient.matchingStacks)){
				iterator.remove();
			}
		}
	}

	@ZenMethod
	public static void removeAll() {
		Iterator<IntegrationRecipe> itr = BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes().iterator();
		while(itr.hasNext()) {
			itr.next();
			itr.remove();
		}
		CraftTweakerAPI.logInfo("Clearing all integration table recipes");
	}
}
