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
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

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
		DistillingRecipe.recipes.add(recipe);
		DistillingRecipe.distillable_items.add(recipe.inputs.get(0));
		DistillingRecipe.distillable_fluids.add(recipe.fluid_in.getFluid());
	}

	@ZenMethod
	public static void remove(IIngredient output) {
		final Predicate<ItemStack> outFilter = Utils.getStackMatcher(output);
		DistillingRecipe.recipes.removeIf(recipe -> outFilter.test(recipe.getOutput()));
	}

	@ZenMethod
	public static void removeAll() {
		DistillingRecipe.recipes.clear();
		ListClear.logMessage("distillery");
	}
}
