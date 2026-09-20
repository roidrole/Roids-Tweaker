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
@ZenExpansion("mods.thermalexpansion.Pulverizer")
@SuppressWarnings("unused")
public class Pulverizer {
    public static final IngredientOverrides oreOverrides = new IngredientOverrides();

    @ZenMethodStatic
    public static void addOreOverride(IIngredient input, boolean value) {
        oreOverrides.put(Utils.getStackMatcher(input), value);
    }

    @ZenMethodStatic
    public static void removeOreOverride(IIngredient input) {
        oreOverrides.remove(Utils.getStackMatcher(input));
    }
}
