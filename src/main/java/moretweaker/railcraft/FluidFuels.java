package moretweaker.railcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.fuel.FluidFuelManager;
import moretweaker.MoreTweaker;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.mixins.railcraft.FluidFuelManagerAccessor;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.railcraft.FluidFuels")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class FluidFuels {
	@ZenMethod
	public static void add(ILiquidStack fuel, @Optional(valueLong = 4800L) final int heatPerBucket) {
		final FluidStack fluid = CraftTweakerMC.getLiquidStack(fuel);
		fluid.amount = 1000;
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				FluidFuelManager.addFuel(fluid, heatPerBucket);
			}

			public String describe() {
				return "Adds a railcraft fluid fuel";
			}
		});
	}

	@ZenMethod
	public static void remove(ILiquidStack fuel) {
		final FluidStack fluid = CraftTweakerMC.getLiquidStack(fuel);
		fluid.amount = 1000;
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				FluidFuelManagerAccessor.getBoilerFuel().keySet().removeIf((f) -> f.getFluid() == fluid.getFluid());
			}

			public String describe() {
				return "Removes a railcraft fluid fuel";
			}
		});
	}

	@ZenMethod
	public static void removeAll() {
		MoreTweaker.queuePostInitAction(new IAction() {
			public void apply() {
				FluidFuelManagerAccessor.getBoilerFuel().clear();
			}

			public String describe() {
				return "Removes all recipes";
			}
		});
	}
}
