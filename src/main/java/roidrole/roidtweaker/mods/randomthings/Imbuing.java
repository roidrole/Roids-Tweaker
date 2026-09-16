package roidrole.roidtweaker.mods.randomthings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import lumien.randomthings.recipes.imbuing.ImbuingRecipeHandler;
import roidrole.roidtweaker.utils.DeferredLoader;
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
        DeferredLoader.postInit(new ListAdd<>(ImbuingRecipeHandler.imbuingRecipes, recipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient output){
        if(output == null){
            CraftTweakerAPI.logError("Output can't be null", new AssertionError());
            return;
        }
        DeferredLoader.postInit(() ->
            ImbuingRecipeHandler.imbuingRecipes.removeIf(recipe ->
                output.matches(MCItemStack.createNonCopy(recipe.getResult()))
            )
        );
    }
    @ZenMethod
    public static void removeRecipe(int index){
        DeferredLoader.postInit(() ->
            ImbuingRecipeHandler.imbuingRecipes.remove(index)
        );
    }

    @ZenMethod
    public static void clearRecipes(){
        DeferredLoader.postInit(new ListClear(ImbuingRecipeHandler.imbuingRecipes, "imbuing recipe"));
    }
}
