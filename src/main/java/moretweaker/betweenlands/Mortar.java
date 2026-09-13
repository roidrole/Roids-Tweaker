package moretweaker.betweenlands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.thebetweenlands.PestleAndMortarRecipeAccessor;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.mortar.PestleAndMortarRecipe;

import java.util.function.Predicate;

@ZenClass("moretweaker.betweenlands.Mortar")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Mortar {
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);

		for(ItemStack stack : CraftTweakerMC.getExamples(input)) {
			BetweenlandsAPI.getInstance().registerPestleAndMortarRecipe(new PestleAndMortarRecipe(out, stack));
		}
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);

		PestleAndMortarRecipeAccessor.getRecipes().removeIf(
			recipe -> outFilter.test(recipe.getOutput(recipe.getInputs()))
		);
	}

	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.logInfo("Removing all recipes for Pestle&Mortar");
		PestleAndMortarRecipeAccessor.getRecipes().clear();
	}
}
