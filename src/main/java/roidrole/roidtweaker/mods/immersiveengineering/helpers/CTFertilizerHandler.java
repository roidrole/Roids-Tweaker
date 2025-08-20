package roidrole.roidtweaker.mods.immersiveengineering.helpers;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import roidrole.roidtweaker.utils.FunctionalInterfaces;

import javax.annotation.Nullable;

public class CTFertilizerHandler implements BelljarHandler.ItemFertilizerHandler, BelljarHandler.FluidFertilizerHandler {
    IIngredient fertilizer;
    FunctionalInterfaces.FloatTriFunction<Object, ItemStack, ItemStack> multiplier;

    public CTFertilizerHandler(IIngredient fertilizer, float multiplier){
        this.fertilizer = fertilizer;
        this.multiplier = (a, b, c) -> multiplier;
        if(fertilizer.getLiquids().isEmpty()){
            BelljarHandler.registerItemFertilizer(this);
        } else {
            BelljarHandler.registerFluidFertilizer(this);
        }
    }

    public CTFertilizerHandler(IIngredient fertilizer, GardenClocheMultiplierFunction multiplierIn){
        this.fertilizer = fertilizer;
        this.multiplier = (fert, seed, soil) -> multiplierIn.returnFloat(
            (fert instanceof ItemStack)?CraftTweakerMC.getIItemStack((ItemStack) fert):CraftTweakerMC.getILiquidStack((FluidStack) fert),
            CraftTweakerMC.getIItemStack(seed),
            CraftTweakerMC.getIItemStack(soil)
        );
        if(fertilizer.getLiquids().isEmpty()){
            BelljarHandler.registerItemFertilizer(this);
        } else {
            BelljarHandler.registerFluidFertilizer(this);
        }
    }

    @Override
    public boolean isValid(ItemStack clocheFertilizer) {
        return fertilizer.matches(CraftTweakerMC.getIItemStack(clocheFertilizer));
    }

    @Override
    public float getGrowthMultiplier(ItemStack clocheFertilizer, ItemStack seed, ItemStack soil, TileEntity tile) {
        return multiplier.apply(clocheFertilizer, seed, soil);
    }

    @Override
    public boolean isValid(@Nullable FluidStack clocheFertilizer) {
        return fertilizer.matches(CraftTweakerMC.getILiquidStack(clocheFertilizer));
    }

    @Override
    public float getGrowthMultiplier(FluidStack clocheFertilizer, ItemStack seed, ItemStack soil, TileEntity tile) {
        return multiplier.apply(clocheFertilizer, seed, soil);
    }

}
