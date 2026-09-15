package roidrole.roidtweaker.mods.randomthings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import lumien.randomthings.recipes.imbuing.ImbuingRecipeHandler;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ModOnly("randomthings")
@ZenRegister
@ZenClass("mods.roidtweaker.randomthings.Imbuing")
@SuppressWarnings("unused")
public class Imbuing {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, IIngredient[] ingredients){
        CTImbuingRecipe recipe = new CTImbuingRecipe(output, input, ingredients);
        DeferredLoader.load(EnumLoadStage.POST_INIT, new ListAdd<>(ImbuingRecipeHandler.imbuingRecipes, recipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient output){
        if(output == null){
            CraftTweakerAPI.logError("Output can't be null", new AssertionError());
            return;
        }
        DeferredLoader.load(EnumLoadStage.POST_INIT, () ->
            ImbuingRecipeHandler.imbuingRecipes.removeIf(recipe ->
                output.matches(MCItemStack.createNonCopy(recipe.getResult()))
            )
        );
    }
    @ZenMethod
    public static void removeRecipe(int index){
        DeferredLoader.load(EnumLoadStage.POST_INIT, () ->
            ImbuingRecipeHandler.imbuingRecipes.remove(index)
        );
    }

    @ZenMethod
    public static void clearRecipes(){
        DeferredLoader.load(EnumLoadStage.POST_INIT, new ListClear(ImbuingRecipeHandler.imbuingRecipes));
    }
}
