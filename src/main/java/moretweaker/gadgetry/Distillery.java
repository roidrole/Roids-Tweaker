package moretweaker.gadgetry;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.machines.recipe.DistillingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
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

@ZenClass("moretweaker.gadgetry.Distillery")
@ZenRegister
@ModOnly("gadgetrymachines")
@SuppressWarnings("unused")
public class Distillery {
	@ZenMethod
	public static void add(IItemStack output, ILiquidStack fluidOutput, IIngredient input, ILiquidStack fluidInput) {
		final ItemStack out = CraftTweakerMC.getItemStack(output);
		final Object in = Utils.getObjectOreStack(input);
		final FluidStack fluidOut = CraftTweakerMC.getLiquidStack(fluidOutput);
		final FluidStack fluidIn = CraftTweakerMC.getLiquidStack(fluidInput);

		DistillingRecipe recipe = new DistillingRecipe(fluidIn, fluidOut, in, out);
		DistillAction.getInstance().addAction(ListAdd.from(DistillingRecipe.recipes, recipe));
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		DistillAction.getInstance().addAction(ListRemove.from(DistillingRecipe.recipes, outFilter, DistillingRecipe::getOutput));
	}

	@ZenMethod
	public static void removeAll() {
		DistillAction.getInstance().addAction(ListClear.from(DistillingRecipe.recipes, "Distillery"));
	}

	public static class DistillAction implements DeferredLoader.IAction {
		private static DistillAction INSTANCE = null;
		private final Collection<DeferredLoader.IAction> actions = new ArrayList<>();

		public static DistillAction getInstance(){
			if(INSTANCE == null){
				INSTANCE = new DistillAction();
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

			DistillingRecipe.distillable_items.clear();
			DistillingRecipe.distillable_fluids.clear();

			for(DistillingRecipe distillingRecipe : DistillingRecipe.recipes) {
				DistillingRecipe.distillable_items.add(distillingRecipe.inputs.get(0));
				DistillingRecipe.distillable_fluids.add(distillingRecipe.fluid_in.getFluid());
			}
			INSTANCE = null;
		}
	}
}
