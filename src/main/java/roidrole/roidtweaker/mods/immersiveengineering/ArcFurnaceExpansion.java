package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModOnly("immersiveengineering")
@ZenExpansion("mods.immersiveengineering.ArcFurnace")
@ZenRegister
@SuppressWarnings("unused")
public class ArcFurnaceExpansion {
    @ZenMethodStatic
    public static void addRecycling(IIngredient ingredient){
        ArcFurnaceRecipe.allowItemForRecycling(CraftTweakerHelper.toObject(ingredient));
    }
    @ZenMethodStatic
    public static void removeRecyclingOutput(IIngredient ingredient){
        ArcFurnaceRecipe.makeItemInvalidRecyclingOutput(CraftTweakerHelper.toObject(ingredient));
    }
}
