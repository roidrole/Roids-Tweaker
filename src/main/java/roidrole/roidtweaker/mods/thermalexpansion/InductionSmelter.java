package roidrole.roidtweaker.mods.thermalexpansion;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.HashMap;
import java.util.Map;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenExpansion("mods.thermalexpansion.InductionSmelter")
@SuppressWarnings("unused")
public class InductionSmelter {
    public static Map<IIngredient, Boolean> oreOverrideMap = new HashMap<>();

    @ZenMethodStatic
    public static void addOreOverride(IIngredient input, boolean value) {
        oreOverrideMap.put(input, value);
    }

    @ZenMethodStatic
    public static void removeOreOverride(IIngredient input) {
        oreOverrideMap.remove(input);
    }
}
