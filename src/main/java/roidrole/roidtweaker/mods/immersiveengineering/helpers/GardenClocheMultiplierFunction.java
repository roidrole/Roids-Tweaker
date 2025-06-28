package roidrole.roidtweaker.mods.immersiveengineering.helpers;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataFloat;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import roidrole.roidtweaker.utils.FunctionalInterfaces;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.roidtweaker.immersiveengineering.GardenClocheMultiplierFunction")
@FunctionalInterface
public interface GardenClocheMultiplierFunction extends FunctionalInterfaces.TriFunction<IIngredient, IItemStack, IItemStack, DataFloat> {
    default float returnFloat(IIngredient a, IItemStack b, IItemStack c){
        return this.apply(a, b, c).asFloat();
    }
}

