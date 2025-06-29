package roidrole.roidtweaker.mixins.f0resources;

import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import org.spongepowered.asm.mixin.Mixin;
import roidrole.roidtweaker.mods.f0resources.IFluidDataExpansion;
import v0id.api.f0resources.world.IFluidData;

@Mixin(IFluidData.class)
public interface IFluidDataMixin extends IFluidDataExpansion {
    default ILiquidStack createLiquidStack(int fluidAmount){
        return CraftTweakerMC.getILiquidStack(((IFluidData) this).createFluidStack(fluidAmount));
    }

    default ILiquidDefinition getLiquid(){
        return CraftTweakerMC.getILiquidDefinition(((IFluidData) this).getFluid());
    }
}
