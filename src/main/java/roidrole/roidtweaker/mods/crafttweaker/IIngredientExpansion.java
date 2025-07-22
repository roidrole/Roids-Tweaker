package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenRegister
@ZenExpansion("crafttweaker.item.IIngredient")
@SuppressWarnings("unused")
public class IIngredientExpansion {
    @ZenMethod
    public static IIngredient[] spread(IIngredient input){
        IIngredient inputRevised = input.amount(1);
        IIngredient[] output = new IIngredient[input.getAmount()];
        Arrays.fill(output, inputRevised);
        return output;
    }
}
