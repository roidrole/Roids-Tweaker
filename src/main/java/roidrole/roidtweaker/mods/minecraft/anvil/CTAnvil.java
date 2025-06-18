package roidrole.roidtweaker.mods.minecraft.anvil;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.roidtweaker.Anvil")
@ZenRegister
public class CTAnvil {
    public static List<AnvilRecipe> recipes = new ArrayList<>(0);

    @ZenMethod
    public static void addRecipe(IIngredient left, IIngredient right, IItemStack output, int xpCost) {
        AnvilRecipe recipe = new AnvilRecipe();
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
        AnvilRecipe recipe = new AnvilRecipe();
            recipe.left = inputs[0];
            if(inputs.length > 1){
                recipe.right = inputs[1];
            }
            recipe.disabled = true;
        recipes.add(recipe);
    }

    @ZenMethod
    public static void remove(IIngredient output){
        AnvilRecipe recipe = new AnvilRecipe();
            recipe.output = output;
            recipe.disabled = true;
            recipe.matchByOutput = true;
        recipes.add(recipe);
    }

    @ZenMethod
    public static void addRepair(IIngredient repaired, IIngredient material, int amount, @Optional int xpCost){
        AnvilRecipe recipe = new AnvilRecipe();
            recipe.left = repaired;
            recipe.right = material;
            recipe.output = amount;
            recipe.xpCost = xpCost;
        recipes.add(recipe);
    }

    @ZenMethod
    public static void addRepair(IIngredient repaired, IIngredient material, float amount, @Optional int xpCost){
        AnvilRecipe recipe = new AnvilRecipe();
            recipe.left = repaired;
            recipe.right = material;
            recipe.output = amount;
            recipe.xpCost = xpCost;
        recipes.add(recipe);
    }
}
