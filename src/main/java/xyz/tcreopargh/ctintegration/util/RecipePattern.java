package xyz.tcreopargh.ctintegration.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.recipes.IRecipeAction;
import crafttweaker.api.recipes.IRecipeFunction;
import crafttweaker.mc1120.CraftTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ZenClass(CTIntegration.CT_PACKAGE + "util.RecipePattern")
@ZenRegister
public class RecipePattern {
    private final String[] pattern;
    private final Map<Character, IIngredient> mapping = new HashMap<>();
    private boolean isMirrored = false;
    private boolean isShapeless = false;
    private String name;
    private IItemStack output;
    private IRecipeFunction recipeFunction;
    private IRecipeAction recipeAction;

    public RecipePattern(String[] pattern) {
        this.pattern = pattern;
    }

    public RecipePattern(IItemStack output, String[] pattern) {
        this.pattern = pattern;
        this.output = output;
    }

    public RecipePattern(String name, IItemStack output, String[] pattern) {
        this.pattern = pattern;
        this.output = output;
        this.name = name;
    }

    @ZenMethod
    public static RecipePattern init(String[] recipePattern) {
        return new RecipePattern(recipePattern);
    }

    @ZenMethod
    public static RecipePattern init(IItemStack output, String[] recipePattern) {
        return new RecipePattern(output, recipePattern);
    }

    @ZenMethod
    public static RecipePattern init(String name, IItemStack output, String[] recipePattern) {
        return new RecipePattern(name, output, recipePattern);
    }

    @ZenMethod
    public RecipePattern with(String character, IIngredient ingredient) {
        if (character.length() != 1) {
            CraftTweakerAPI.logError("Mapping key must be one single character", new IllegalArgumentException());
        } else {
            char firstChar = character.charAt(0);
            if (Character.isWhitespace(firstChar)) {
                CraftTweakerAPI.logError("You can't map a whitespace character!", new IllegalArgumentException());
            } else {
                mapping.put(firstChar, ingredient);
            }
        }
        return this;
    }

    @ZenMethod
    public RecipePattern withOutput(IItemStack output) {
        this.output = output;
        return this;
    }

    @ZenMethod
    public RecipePattern and(String character, IIngredient ingredient) {
        return this.with(character, ingredient);
    }

    @ZenMethod
    public RecipePattern setMirrored(boolean isMirrored) {
        this.isMirrored = isMirrored;
        return this;
    }

    @ZenMethod
    public RecipePattern setName(String name) {
        this.name = name;
        return this;
    }

    @ZenMethod
    public RecipePattern setShapeless(boolean isShapeless) {
        this.isShapeless = isShapeless;
        return this;
    }

    @ZenMethod
    public RecipePattern setFunction(IRecipeFunction function) {
        this.recipeFunction = function;
        return this;
    }

    @ZenMethod
    public RecipePattern setAction(IRecipeAction action) {
        this.recipeAction = action;
        return this;
    }


    @ZenGetter("ingredients")
    public IIngredient[][] getIngredients() {
        IIngredient[][] grid = new IIngredient[pattern.length][];
        for(int indexR = 0; indexR < pattern.length; indexR++){
            grid[indexR] = new IIngredient[pattern[indexR].length()];
            int indexC = 0;
            for (char ch : pattern[indexR].toCharArray()){
                IIngredient current;
                if(Character.isWhitespace(ch) || !mapping.containsKey(ch)){
                    current = null;
                } else{
                    current = mapping.get(ch);
                }
                grid[indexR][indexC] = current;
                indexC++;
            }
        }
        return grid;
    }

    @ZenGetter("shapelessIngredients")
    public IIngredient[] getShapelessIngredients() {
        return Arrays.stream(getIngredients())
            .flatMap(Arrays::stream)
            .toArray(IIngredient[]::new)
        ;
    }

    @ZenMethod
    public RecipePattern map(Map<String, IIngredient> mapping) {
        for (Map.Entry<String, IIngredient> entry : mapping.entrySet()) {
            this.with(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @ZenMethod
    public void build() {
        if (isShapeless) {
            IIngredient[] grid = getShapelessIngredients();
            if (grid.length == 0){
                CraftTweakerAPI.logError("The pattern is empty", new IllegalArgumentException());
                return;
            }
            CraftTweaker.INSTANCE.recipes.addShapeless(name, output, grid, recipeFunction, recipeAction);
        } else {
            IIngredient[][] grid = getIngredients();
            if (grid.length == 0) {
                CraftTweakerAPI.logError("The pattern is empty", new IllegalArgumentException());
                return;
            }
            if (isMirrored) {
                CraftTweaker.INSTANCE.recipes.addShapedMirrored(name, output, grid, recipeFunction, recipeAction);
            } else {
                CraftTweaker.INSTANCE.recipes.addShaped(name, output, grid, recipeFunction, recipeAction);
            }
        }
    }
}

