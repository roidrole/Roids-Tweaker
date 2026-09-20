package moretweaker.fossil;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.fossils.ARecipeAnalyser;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.fossil.Analyzer")
@ZenRegister
@ModOnly("fossil")
@SuppressWarnings("unused")
public class Analyzer {
	@ZenMethod
	public static void addOutput(IIngredient input, IItemStack output, final double weight) {
		final ItemStack[] in = CraftTweakerMC.getExamples(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		DeferredLoader.postInit(() -> {
			for(ItemStack stack : in) {
				RecipeAnalyzer recipe = FAMachineRecipeRegistry.getAnalyzerRecipeForItem(stack);
				if (recipe == null) {
					recipe = new RecipeAnalyzer(stack);
					FAMachineRecipeRegistry.analyzerRecipes.add(recipe);
				}

				recipe.addOutput(out, (float)weight);
			}
		});
	}

	@ZenMethod
	public static void removeByInput(IIngredient input) {
		final Predicate<ItemStack> inputFilter = Utils.getStackMatcher(input);
		DeferredLoader.postInit(ListRemove.from(
			FAMachineRecipeRegistry.analyzerRecipes,
			inputFilter,
			RecipeAnalyzer::getInput
		));
	}

	@ZenMethod
	public static void removeByOutput(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(() -> {
			for (RecipeAnalyzer recipe : FAMachineRecipeRegistry.analyzerRecipes) {
				if (recipe.getDisplayMap().values().removeIf(outputFilter)) {
					((ARecipeAnalyser) recipe).setTotalWeight(recipe.getDisplayMap().keySet().stream().reduce(0.0F, Float::sum));
				}
			}
		});
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(FAMachineRecipeRegistry.analyzerRecipes, "f&a analyser"));
	}
}
