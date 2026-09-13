package roidrole.roidtweaker.mods.thermalexpansion;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import roidrole.roidtweaker.utils.IngredientOverrides;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenExpansion("mods.thermalexpansion.RedstoneFurnace")
@SuppressWarnings("unused")
public class RedstoneFurnace {
    public static IngredientOverrides foodOverrides = new IngredientOverrides();
    public static IngredientOverrides oreOverrides = new IngredientOverrides();

    @ZenMethodStatic
    public static void addFoodOverride(IIngredient input, boolean value){
        foodOverrides.put(Utils.getStackMatcher(input), value);
    }
    @ZenMethodStatic
    public static void removeFoodOverride(IIngredient input){
        foodOverrides.remove(Utils.getStackMatcher(input));
    }

    @ZenMethodStatic
    public static void addOreOverride(IIngredient input, boolean value){
        oreOverrides.put(Utils.getStackMatcher(input), value);
    }
    @ZenMethodStatic
    public static void removeOreOverride(IIngredient input){
        oreOverrides.remove(Utils.getStackMatcher(input));
    }
}
