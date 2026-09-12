package moretweaker.erebus;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import erebus.ModItems;
import erebus.recipes.SmoothieMakerRecipe;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.erebus.SmoothieMakerRecipeAccessor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.erebus.SmoothieMaker")
@ZenRegister
@ModOnly("erebus")
public class SmoothieMaker {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] inputs, ILiquidStack[] fluids) {
		addRecipe(output, CraftTweakerMC.getIItemStack(new ItemStack(ModItems.MATERIALS, 1, 59)), inputs, fluids);
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack container, IIngredient[] inputs, ILiquidStack[] fluids) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack c = CraftTweakerMC.getItemStack(container);
		final Object[] in = Inputs.getObjects(inputs);

		for(Object obj : in) {
			if (!(obj instanceof ItemStack) && !(obj instanceof String)) {
				CraftTweakerAPI.logError("For SmoothieMaker.addRecipe: Only IItemSTack and IOreDictEntry are allowed.");
				return;
			}
		}

		final FluidStack[] fl = CraftTweakerMC.getLiquidStacks(fluids);
		if (in.length <= 4 && fl.length <= 4) {
			MoreTweaker.queuePostInitAction(new IAction() {
				public void apply() {
					SmoothieMakerRecipe.addRecipe(out, c, fl, in);
				}

				public String describe() {
					return "Adds an smoothie maker recipe";
				}
			});
		} else {
			CraftTweakerAPI.logError("For SmoothieMaker.addRecipe: Not more than 4 ingredients and 4 fluids are allowed.");
		}
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Object out = Inputs.getObject(output);
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				SmoothieMakerRecipeAccessor.getRecipes().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
			}

			public String describe() {
				return "Removes some smoothie maker recipes";
			}
		});
	}

	@ZenMethod
	public static void removeAll() {
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				SmoothieMakerRecipeAccessor.getRecipes().clear();
			}

			public String describe() {
				return "Removes all recipes for the smoothie maker";
			}
		});
	}
}
