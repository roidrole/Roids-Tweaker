package roidrole.roidtweaker.utils;

import com.google.common.base.Predicates;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.item.IngredientUnknown;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.roidtweaker.utils.predicates.*;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public abstract class Utils {
    public static int ceilDiv(int x, int y){
        return -Math.floorDiv(-x,y);
    }

    public static ItemStack getRandomStack(IIngredient input, Random random){
        if(input == null || input.getItems().isEmpty()){
            return ItemStack.EMPTY;
        }
        List<IItemStack> list = input.getItems();
        IItemStack element = list.get(random.nextInt(list.size()));
        return CraftTweakerMC.getItemStack(element);
    }
    public static ItemStack getFirstStack(IIngredient input){
        if(input == null || input.getItems().isEmpty()){
            return ItemStack.EMPTY;
        }
        return CraftTweakerMC.getItemStack(input.getItems().get(0));
    }

    public static Ingredient[] getIngredients(IIngredient[] ingredients){
        final Ingredient[] out = new Ingredient[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            out[i] = CraftTweakerMC.getIngredient(ingredients[i]);
        }
        return out;
    }
    public static Ingredient[][] getIngredients(IIngredient[][] ingredients){
        final Ingredient[][] out = new Ingredient[ingredients.length][];
        for (int i = 0; i < ingredients.length; i++) {
            out[i] = Utils.getIngredients(ingredients[i]);
        }
        return out;
    }

    //Essentially a copy of CraftTweakerMC.getIngredient, but returning a simple predicate
    //Guarantees: Two equal IIngredient must yield two equal Predicate. No guarantee for equivalent but not equal.
    public static Predicate<ItemStack> getStackMatcher(IIngredient ingredient){
        if (ingredient == null || ingredient instanceof IngredientUnknown) {
            return Predicates.alwaysFalse();
        } else if (ingredient instanceof IngredientAny) {
            return Predicates.alwaysTrue();
        } else if (ingredient instanceof IOreDictEntry) {
            return new OreMatching(((IOreDictEntry) ingredient).getName());
        } else if (ingredient instanceof IItemStack) {
            ItemStack stack = CraftTweakerMC.getItemStack((IItemStack) ingredient);
            if(stack.hasTagCompound() && ((IItemStack)ingredient).getMatchTagExact()){
                return new TagMatching(stack);
            } else if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                return new ItemMatching(stack.getItem());
            } else {
                return new StackMatching(stack.getItem(), stack.getItemDamage());
            }
        } else {
            return new IIngredientMatching(ingredient);
        }
    }

    public static boolean anyMatchIngredient(Predicate<ItemStack> predicate, Iterable<Ingredient> ingredients){
        for (Ingredient ingredient : ingredients){
            if(PredicateAnyOfIterable.test(predicate, ingredient.getMatchingStacks())){
                return true;
            }
        }
        return false;
    }

    public static Object getObject(IIngredient ingredient){
        /*
        I am trying to find the reciprocal of this (from CraftingHelper), roughly.
        The fact that some mods require Object in their API is disgusting.
            if (obj instanceof Ingredient)
                return (Ingredient)obj;
            else if (obj instanceof ItemStack)
                return Ingredient.fromStacks(((ItemStack)obj).copy());
            else if (obj instanceof Item)
                return Ingredient.fromItem((Item)obj);
            else if (obj instanceof Block)
                return Ingredient.fromStacks(new ItemStack((Block)obj, 1, OreDictionary.WILDCARD_VALUE));
            else if (obj instanceof String)
                return new OreIngredient((String)obj);
            else if (obj instanceof JsonElement)
                throw new IllegalArgumentException("JsonObjects must use getIngredient(JsonObject, JsonContext)");
            return null;
         */
        if(ingredient instanceof IOreDictEntry){
            return ((IOreDictEntry)ingredient).getName();
        } else {
            return Utils.getFirstStack(ingredient);
        }
    }
    public static Object[] getObjects(IIngredient[] ingredient){
        Object[] out = new Object[ingredient.length];
        for (int i = 0; i < ingredient.length; i++) {
            out[i] = Utils.getObject(ingredient[i]);
        }
        return out;
    }
}
