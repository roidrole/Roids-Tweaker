package moretweaker.gadgetry;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.machines.recipe.CombustionValues;
import net.minecraftforge.fluids.Fluid;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.gadgetry.CombustionGenerator")
@ZenRegister
@ModOnly("gadgetrymachines")
@SuppressWarnings("unused")
public class CombustionGenerator {
	@ZenMethod
	public static void add(ILiquidStack liquid, int energy) {
		final Fluid fluid = CraftTweakerMC.getLiquidStack(liquid).getFluid();
		final int internal = energy / 1000;
		ListAdd.add(CombustionValues.values, fluid, internal);
	}

	@ZenMethod
	public static void remove(ILiquidStack liquid) {
		final Fluid fluid = CraftTweakerMC.getLiquidStack(liquid).getFluid();
		ListRemove.removeKey(CombustionValues.values, fluid);
	}

	@ZenMethod
	public static void removeAll() {
		ListClear.clear(CombustionValues.values, "Gadgetry combustion generator");
	}
}
