package roidrole.roidtweaker.mods.minecraft.anvil;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.roidtweaker.minecraft.Anvil")
@ZenRegister
@SuppressWarnings("unused")
public class CTAnvil {
    public static List<AnvilRecipes> recipes = new ArrayList<>(0);

    @ZenMethod
    public static void addRecipe(IIngredient left, IIngredient right, IItemStack output, int xpCost) {
        AnvilRecipes recipe = new AnvilRecipes();
            recipe.left = left;
            recipe.right = right;
            recipe.output = CraftTweakerMC.getItemStack(output);
            recipe.xpCost = xpCost;
        recipes.add(recipe);
    }
    @ZenMethod
    public static void addRecipeShapeless(IIngredient left, IIngredient right, IItemStack output, int xpCost) {
        AnvilRecipes.Shapeless recipe = new AnvilRecipes.Shapeless();
        recipe.left = left;
        recipe.right = right;
        recipe.output = CraftTweakerMC.getItemStack(output);
        recipe.xpCost = xpCost;
        recipes.add(recipe);
    }

    @ZenMethod
    public static void addRecipes(IIngredient left, IIngredient[] right, IItemStack[] output, int[] xpCost) {
        for (int i = 0; i < right.length; i++) {
            addRecipe(left, right[i], output[i], xpCost[i]);
        }
    }

    @ZenMethod
    public static void remove(IIngredient[] inputs){
        AnvilRecipes.DisabledRecipe recipe = new AnvilRecipes.DisabledRecipe();
            recipe.left = inputs[0];
            if(inputs.length > 1){
                recipe.right = inputs[1];
            } else {
                recipe.right = IngredientAny.INSTANCE;
            }
        recipes.add(recipe);
    }

    @ZenMethod
    public static void remove(IIngredient output){
        AnvilRecipes.DisabledRecipeOutput recipe = new AnvilRecipes.DisabledRecipeOutput();
        recipe.output = output;
        recipes.add(recipe);
    }

    @ZenMethod
    public static void addRepair(IIngredient repaired, IIngredient material, int amount, @Optional int xpCost){
        AnvilRecipes.Repair recipe = new AnvilRecipes.Repair();
            recipe.left = repaired;
            recipe.right = material;
            recipe.repair = amount;
            recipe.xpCost = xpCost;
        recipes.add(recipe);
    }

    @ZenMethod
    public static void addRepair(IIngredient repaired, IIngredient material, float amount, @Optional int xpCost){
        AnvilRecipes.Repair recipe = new AnvilRecipes.Repair();
            recipe.left = repaired;
            recipe.right = material;
            recipe.repairRatio = amount;
            recipe.xpCost = xpCost;
        recipes.add(recipe);
    }
}
