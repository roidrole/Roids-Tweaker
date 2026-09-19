package moretweaker.quacklib;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import kotlin.Unit;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import therealfarfetchd.quacklib.common.api.recipe.AlloyFurnaceRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ZenClass("moretweaker.quacklib.AlloyFurnace")
@ZenRegister
@ModOnly("quacklib")
public class AlloyFurnace {
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] inputs) {
		final List<IngredientTemplate> templates = new ArrayList<>(inputs.length);

		for(IIngredient part : inputs) {
			templates.add(new IngredientTemplate(part));
		}

		DeferredLoader.postInit(FunctionInvoke.from(
			AlloyFurnaceRecipes.INSTANCE::addRecipe,
			template -> {
				template.setOutput(new IngredientTemplate(output));
				template.setInputs(templates);
				return Unit.INSTANCE;
			})
		);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output) {
		final Predicate<ItemStack> filter = Utils.getStackMatcher(output);
		DeferredLoader.postInit(ListRemove.from(
			AlloyFurnaceRecipes.INSTANCE.getRecipesList(),
			filter,
			recipe -> recipe.getSecond().makeStack()
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(AlloyFurnaceRecipes.INSTANCE.getRecipesList(), "quack alloy furnace"));
	}
}
