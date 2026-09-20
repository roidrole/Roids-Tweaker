package roidrole.roidtweaker.mods.randomthings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import lumien.randomthings.recipes.imbuing.ImbuingRecipe;
import lumien.randomthings.recipes.imbuing.ImbuingRecipeHandler;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListAdd;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Predicate;


@ModOnly("randomthings")
@ZenRegister
@ZenClass("mods.roidtweaker.randomthings.Imbuing")
@SuppressWarnings("unused")
public class Imbuing {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, IIngredient[] ingredients){
        CTImbuingRecipe recipe = new CTImbuingRecipe(output, input, ingredients);
        DeferredLoader.postInit(ListAdd.from(ImbuingRecipeHandler.imbuingRecipes, recipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient output){
        if(output == null){
            CraftTweakerAPI.logError("Output can't be null", new AssertionError());
            return;
        }
		Predicate<ItemStack> filter = Utils.getStackMatcher(output);
        DeferredLoader.postInit(ListRemove.from(
            ImbuingRecipeHandler.imbuingRecipes,
            filter,
            ImbuingRecipe::getResult

        ));
    }
    @ZenMethod
    public static void removeRecipe(int index){
        DeferredLoader.postInit(FunctionInvoke.from(
            ImbuingRecipeHandler.imbuingRecipes::remove,
            index
        ));
    }

    @ZenMethod
    public static void clearRecipes(){
        DeferredLoader.postInit(ListClear.from(ImbuingRecipeHandler.imbuingRecipes, "imbuing recipe"));
    }
}
