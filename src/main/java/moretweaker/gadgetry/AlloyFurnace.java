package moretweaker.gadgetry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.core.recipe.AlloyRecipe;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.gadgetry.AlloyFurnace")
@ZenRegister
@ModOnly("gadgetrycore")
@SuppressWarnings("unused")
public class AlloyFurnace {
	@ZenMethod
	public static void add(IItemStack output, IIngredient[] inputs) {
		if (inputs.length > 3) {
			CraftTweakerAPI.logError("Can't add AlloyFurnace-Recipe with more than three inputs.");
		}
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Object[] in = Utils.getObjects(inputs, Utils::getObjectOreStack);

		AlloyRecipe.addRecipe(new AlloyRecipe(out, in));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		AlloyRecipe.recipes.removeIf(recipe -> outFilter.test(recipe.getOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		AlloyRecipe.recipes.clear();
		ListClear.logMessage("AlloyFurnace");
	}
}
