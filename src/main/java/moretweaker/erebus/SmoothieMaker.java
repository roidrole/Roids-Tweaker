package moretweaker.erebus;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import erebus.ModItems;
import erebus.recipes.SmoothieMakerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.erebus.SmoothieMakerRecipeAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.erebus.SmoothieMaker")
@ZenRegister
@ModOnly("erebus")
@SuppressWarnings("unused")
public class SmoothieMaker {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] inputs, ILiquidStack[] fluids) {
		addRecipe(output, CraftTweakerMC.getIItemStack(new ItemStack(ModItems.MATERIALS, 1, 59)), inputs, fluids);
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack container, IIngredient[] inputs, ILiquidStack[] fluids) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final ItemStack c = CraftTweakerMC.getItemStack(container);
		final Object[] in = Utils.getObjects(inputs, Utils::getObjectOreStack);

		final FluidStack[] fl = CraftTweakerMC.getLiquidStacks(fluids);

		if (in.length > 4 || fl.length > 4) {
			CraftTweakerAPI.logError("For SmoothieMaker.addRecipe: Not more than 4 ingredients and 4 fluids are allowed.");
			return;
		}
		DeferredLoader.postInit(FunctionInvoke.from(SmoothieMakerRecipe::addRecipe, out, c, fl, in));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			SmoothieMakerRecipeAccessor.getRecipes(),
			outputFilter,
			SmoothieMakerRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(SmoothieMakerRecipeAccessor.getRecipes(), "smoothie maker"));
	}
}
