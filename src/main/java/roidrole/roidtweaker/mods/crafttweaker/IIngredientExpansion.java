package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Collections;
import java.util.List;

@ZenRegister
@ZenExpansion("crafttweaker.item.IIngredient")
@SuppressWarnings("unused")
public class IIngredientExpansion {
    @ZenMethod
    public static List<IIngredient> spread(IIngredient input){
        return Collections.nCopies(input.getAmount(), input.amount(1));
    }
}
