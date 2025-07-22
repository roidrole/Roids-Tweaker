package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.FluidData")
@ModOnly("f0-resources")
@SuppressWarnings("unused")
public class FluidData {
    public final v0id.f0resources.chunk.FluidData internal;

    public FluidData(v0id.f0resources.chunk.FluidData internal){
        this.internal = internal;
    }

    @ZenMethod
    public static FluidData create(ILiquidStack fluid, long amount){
        return new FluidData(new v0id.f0resources.chunk.FluidData()){{
            this.setStack(fluid);
            this.setAmount(amount);
            this.internal.generatedAmount = 0;
        }};
    }

    @ZenMethod
    public ILiquidStack getStack(@Optional int amount){
        if(amount == 0){amount = 1;}
        return CraftTweakerMC.getILiquidStack(this.internal.createFluidStack(amount));
    }
    @ZenMethod
    public void setStack(ILiquidStack stack){
        this.internal.setFluid(CraftTweakerMC.getLiquidStack(stack).getFluid());
    }

    @ZenMethod
    @ZenGetter
    public long getAmount(){
        return this.internal.getFluidAmount();
    }
    @ZenMethod
    @ZenSetter
    public void setAmount(long amount){
        this.internal.setFluidAmount(amount);
    }

    @ZenMethod
    public ILiquidStack mine(long quantity){
        if(quantity < this.getAmount()){
            quantity = this.getAmount();
            this.setAmount(0);
        } else {
            this.setAmount(this.getAmount() - quantity);
        }
        return this.getStack((int) quantity);
    } //Breaks isDrainRateBasedOnFluidAmount. Should mention that
}
