package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.FluidData")
@ModOnly("f0-resources")
public class FluidData {
    public final v0id.f0resources.chunk.FluidData internal;

    public FluidData(v0id.f0resources.chunk.FluidData internal){
        this.internal = internal;
    }

    @ZenMethod
    public static FluidData create(ILiquidStack fluid, long amount){
        FluidData data = new FluidData(new v0id.f0resources.chunk.FluidData());
            data.setStack(fluid);
            data.setAmount(amount);
            data.internal.generatedAmount = 0;
        return data;
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
    public long getAmount(){
        return this.internal.getFluidAmount();
    }
    @ZenMethod
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
