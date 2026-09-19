package moretweaker.gadgetry;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.machines.recipe.CombustionValues;
import net.minecraftforge.fluids.Fluid;
import roidrole.roidtweaker.utils.actions.ListClear;
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
		CombustionValues.values.put(fluid, internal);
	}

	@ZenMethod
	public static void remove(ILiquidStack liquid) {
		final Fluid fluid = CraftTweakerMC.getLiquidStack(liquid).getFluid();
		CombustionValues.values.remove(fluid);
	}

	@ZenMethod
	public static void removeAll() {
		CombustionValues.values.clear();
		ListClear.logMessage("combustion generator");
	}
}
