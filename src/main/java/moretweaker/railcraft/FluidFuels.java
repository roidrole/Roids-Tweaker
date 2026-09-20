package moretweaker.railcraft;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.railcraft.AccFluidFuelManager;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;

@ZenClass("moretweaker.railcraft.FluidFuels")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class FluidFuels {
	@ZenMethod
	public static void add(ILiquidStack fuel, @Optional(valueLong = 4800L) final int heatPerBucket) {
		final FluidStack fluid = CraftTweakerMC.getLiquidStack(fuel);
		DeferredLoader.postInit(FunctionInvoke.from(
			FluidFuelManager::addFuel,
			fluid,
			heatPerBucket
		));
	}

	@ZenMethod
	public static void remove(ILiquidStack fuel) {
		final FluidStack fluid = CraftTweakerMC.getLiquidStack(fuel);
		fluid.amount = 1;
		DeferredLoader.postInit(ListRemove.from(
			AccFluidFuelManager.getBoilerFuel().keySet(),
			Predicate.isEqual(fluid)
		));
	}

	@ZenMethod
	public static void removeAll() {
		DeferredLoader.postInit(ListClear.from(AccFluidFuelManager.getBoilerFuel(), "railcraft fluid fuel"));
	}
}
