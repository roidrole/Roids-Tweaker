package moretweaker.gadgetry;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.machines.recipe.GrindingRecipe;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.gadgetry.Grinder")
@ZenRegister
@ModOnly("gadgetrymachines")
@SuppressWarnings("unused")
public class Grinder {
	@ZenMethod
	public static void add(IItemStack output, IIngredient input) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Object in = Utils.getObjectOreStack(input);

		GrindingRecipe recipe = new GrindingRecipe(out, in);
		GrindingRecipe.recipes.add(recipe);
		GrindingRecipe.grindables.add(recipe.inputs.get(0));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);

		GrindingRecipe.recipes.removeIf(recipe -> outFilter.test(recipe.getOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		GrindingRecipe.recipes.clear();
		ListClear.logMessage("Grinder");
	}
}
