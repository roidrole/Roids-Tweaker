package moretweaker.erebus;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import erebus.recipes.OfferingAltarRecipe;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.erebus.OfferingAltarRecipeAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.erebus.OfferingAltar")
@ZenRegister
@ModOnly("erebus")
@SuppressWarnings("unused")
public class OfferingAltar {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, IIngredient input3) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);

		final Object in1 = Utils.getObject(input1);
		final Object in2 = Utils.getObject(input2);
		final Object in3 = Utils.getObject(input3);

		DeferredLoader.postInit(FunctionInvoke.from(OfferingAltarRecipe::addRecipe, out, in1, in2, in3));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> outputFilter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			OfferingAltarRecipeAccessor.getList(),
			outputFilter,
			OfferingAltarRecipe::getOutput
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(OfferingAltarRecipeAccessor.getList(), "offering altar"));
	}
}
