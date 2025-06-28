package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModOnly("immersiveengineering")
@ZenExpansion("mods.immersiveengineering.MetalPress")
@ZenRegister
@SuppressWarnings("unused")
public class MetalPressExpansion {
    @ZenMethodStatic
    public static void addRecipeNBT(IItemStack output, IIngredient input, IItemStack mold, int energy) {
        ComparableItemStack compMold = ApiUtils.createComparableItemStack(CraftTweakerHelper.toStack(mold), true);
        MetalPressRecipe.recipeList.put(compMold, new MetalPressRecipe(
            CraftTweakerMC.getItemStack(output),
            ApiUtils.createIngredientStack(CraftTweakerHelper.toObject(input)).setUseNBT(true),
            compMold,
            energy
        ));
    }
}
