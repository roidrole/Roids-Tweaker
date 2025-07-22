package be.lorexe.mekatweaker.crafttweaker.gas;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ModOnly("mekanism")
@ZenClass("mods.mekatweaker.GasFactory")
@SuppressWarnings("unused")
public class GasFactory {

	@ZenMethod
	public static GasRepresentation createGas(ILiquidStack stackFluid){
		return new GasRepresentation(CraftTweakerMC.getFluid(stackFluid.getDefinition()));
	}
	@ZenMethod
	public static GasRepresentation createGas(String unlocalizedName, String icon){
		return new GasRepresentation(unlocalizedName, icon);
	}
	@ZenMethod
	public static GasRepresentation createGas(String unlocalizedName, int color){
		return new GasRepresentation(unlocalizedName, color);
	}

	//Usage of the above methods is preferred as they return a minimal GasRepresentation ready for building. They are kept for backward-compat
	@ZenMethod
	@Deprecated
	public static GasRepresentation createGas(String unlocalizedName) {
		return new GasRepresentation(unlocalizedName);
	}
	@ZenMethod
	@Deprecated
	public static GasRepresentation createFromFluid(ILiquidStack stackFluid) {
		Fluid fluid = (Fluid) stackFluid.getDefinition().getInternal();
		return new GasRepresentation(fluid);
	}
}
