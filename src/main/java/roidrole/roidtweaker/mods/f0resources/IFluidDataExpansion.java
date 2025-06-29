package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("f0-resources")
@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.IFluidData")
public interface IFluidDataExpansion {

    @ZenMethod
    ILiquidStack createLiquidStack(int fluidAmount);

    @ZenMethod
    ILiquidDefinition getLiquid();

    @ZenMethod
    long getFluidAmount();

    @ZenMethod
    long getGeneratedAmount();

    @ZenMethod
    void setFluid(Fluid f);

    @ZenMethod
    void setFluidAmount(long i);
}
