package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IngredientOr;
import crafttweaker.api.item.IngredientUnknown;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenMethodStatic;

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

    @ZenMethod
    public static IIngredient orArray(IIngredient instance, IIngredient[] ingredients){
        if(instance == IngredientUnknown.INSTANCE){
            return new IngredientOr(ingredients);
        }
        IIngredient[] singleArray = new IIngredient[ingredients.length + 1];
        singleArray[0] = instance;
        System.arraycopy(ingredients, 0, singleArray, 1, singleArray.length);
        return new IngredientOr(singleArray);
    }

    @ZenMethodStatic
    public static IIngredient getEmpty(){
        return IngredientUnknown.INSTANCE;
    }
}
