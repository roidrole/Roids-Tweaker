package moretweaker.gadgetry;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.machines.recipe.GrindingRecipe;
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
		GrinderAction.getInstance().addAction(ListAdd.from(GrindingRecipe.recipes, recipe));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		GrinderAction.getInstance().addAction(ListRemove.from(GrindingRecipe.recipes, outFilter, GrindingRecipe::getOutput));
	}

	@ZenMethod
	public static void removeAll() {
		GrinderAction.getInstance().addAction(ListClear.from(GrindingRecipe.recipes, "Grinder"));
	}

	public static class GrinderAction implements DeferredLoader.IAction {
		private static GrinderAction INSTANCE = null;
		private final Collection<DeferredLoader.IAction> actions = new ArrayList<>();

		public static GrinderAction getInstance(){
			if(INSTANCE == null){
				INSTANCE = new GrinderAction();
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
			GrindingRecipe.grindables.clear();

			for(GrindingRecipe grindingRecipe : GrindingRecipe.recipes) {
				GrindingRecipe.grindables.add(grindingRecipe.inputs.get(0));
			}
			INSTANCE = null;
		}
	}
}
