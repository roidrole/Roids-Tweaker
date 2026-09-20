package moretweaker.gadgetry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.core.recipe.AlloyRecipe;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Collection;
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

		AlloyRecipe recipe = new AlloyRecipe(out, in);
		AlloyAction.getInstance().addAction(ListAdd.from(AlloyRecipe.recipes, recipe));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		AlloyAction.getInstance().addAction(ListRemove.from(AlloyRecipe.recipes, outFilter, AlloyRecipe::getOutput));
	}

	@ZenMethod
	public static void removeAll() {
		AlloyAction.getInstance().addAction(ListClear.from(AlloyRecipe.recipes, "Alloy Furnace"));
	}

	public static class AlloyAction implements DeferredLoader.IAction {
		private static AlloyAction INSTANCE = null;
		private final Collection<DeferredLoader.IAction> actions = new ArrayList<>();

		public static AlloyAction getInstance(){
			if(INSTANCE == null){
				INSTANCE = new AlloyAction();
				DeferredLoader.postInit(INSTANCE);
			}
			return INSTANCE;
		}

		public void addAction(DeferredLoader.IAction action){
			this.actions.add(action);
		}

		@Override
		public void load() {
			actions.forEach(DeferredLoader.IAction::load);

			AlloyRecipe.metals.clear();
			AlloyRecipe.additives1.clear();
			AlloyRecipe.additives2.clear();

			for(AlloyRecipe alloyRecipe : AlloyRecipe.recipes) {
				AlloyRecipe.metals.add(alloyRecipe.inputs.get(0));
				AlloyRecipe.additives1.add(alloyRecipe.inputs.get(1));
				AlloyRecipe.additives2.add(alloyRecipe.inputs.get(2));
			}
			INSTANCE = null;
		}
	}
}
