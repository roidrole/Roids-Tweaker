package moretweaker.fossil;

import com.google.common.collect.Iterables;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@ZenClass("moretweaker.fossil.Analyzer")
@ZenRegister
@ModOnly("fossil")
@SuppressWarnings("unused")
public class Analyzer {
	@ZenMethod
	public static void addOutput(IIngredient input, IItemStack output, final double weight) {
		final CraftingPart in = Inputs.getPart(input);
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				for(ItemStack stack : in.toStackList()) {
					RecipeAnalyzer recipe = FAMachineRecipeRegistry.getAnalyzerRecipeForItem(stack);
					if (recipe == null) {
						recipe = new RecipeAnalyzer(stack);
						FAMachineRecipeRegistry.analyzerRecipes.add(recipe);
					}

					recipe.addOutput(out.copy(), (float)weight);
				}

			}

			public String describe() {
				return "Adds some analyzer recipes";
			}
		});
	}

	@ZenMethod
	public static void removeByInput(IIngredient input) {
		final Object in = Inputs.getObject(input);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				FAMachineRecipeRegistry.analyzerRecipes.removeIf((r) -> Inputs.matchesForRemoval(in, r.getInput()));
			}

			public String describe() {
				return "Removes some analyzer recipes by input";
			}
		});
	}

	@ZenMethod
	public static void removeByOutput(IIngredient output) {
		final Object out = Inputs.getObject(output);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				Set<RecipeAnalyzer> newRecipes = new HashSet<>();
				Iterator<RecipeAnalyzer> itr = FAMachineRecipeRegistry.analyzerRecipes.iterator();

				while(itr.hasNext()) {
					RecipeAnalyzer recipe = itr.next();
					if (Iterables.tryFind(recipe.getDisplayMap().values(), (s) -> Inputs.matchesForRemoval(out, s)).isPresent()) {
						itr.remove();
						RecipeAnalyzer r = new RecipeAnalyzer(recipe.getInput());
						float weight = 0.0F;
						boolean outputAdded = false;

						while(weight <= recipe.getTotalWeight()) {
							Map.Entry<Float, ItemStack> entry = recipe.getDisplayMap().higherEntry(weight);
							if (entry == null) {
								break;
							}

							float chance = entry.getKey() - weight;
							weight = entry.getKey();
							if (!Inputs.matchesForRemoval(out, entry.getValue())) {
								r.addOutput(entry.getValue(), chance);
								outputAdded = true;
							}
						}

						if (outputAdded) {
							newRecipes.add(r);
						}
					}
				}

				FAMachineRecipeRegistry.analyzerRecipes.addAll(newRecipes);
			}

			public String describe() {
				return "Removes some analyzer recipes by output";
			}
		});
	}

	@ZenMethod
	public static void removeAll() {
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				FAMachineRecipeRegistry.analyzerRecipes.clear();
			}

			public String describe() {
				return "Removes all f&a analyzer recipes";
			}
		});
	}
}
