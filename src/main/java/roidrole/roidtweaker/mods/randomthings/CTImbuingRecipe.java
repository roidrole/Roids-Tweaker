package roidrole.roidtweaker.mods.randomthings;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientUnknown;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import lumien.randomthings.recipes.imbuing.ImbuingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import roidrole.roidtweaker.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Collectors;

public class CTImbuingRecipe extends ImbuingRecipe {
    IIngredient center;
    IIngredient[] ingredients;

    public CTImbuingRecipe(IItemStack output, IIngredient input, IIngredient[] ingredients) {
        super(
            Utils.getFirstStack(input),
            CraftTweakerMC.getItemStack(output),
            ItemStack.EMPTY
        );
        this.center = input;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matchesItemHandler(IItemHandler iItemHandler) {
        //Center
        IItemStack center = MCItemStack.createNonCopy(iItemHandler.getStackInSlot(3));
        if(!this.center.matches(center)){
            return false;
        }

        //Loops through slots finding if they match an ingredient from the recipe
        //Can't exit early because some slots might not match any ingredient (null exists!)
        BitSet ingMatched = new BitSet(ingredients.length);
        for (int i = 0; i < 3; i++) {
            IItemStack slotStack = MCItemStack.createNonCopy(iItemHandler.getStackInSlot(i));
            if(slotStack == null){continue;}
            for(IIngredient recipeIng : ingredients){
                if(!ingMatched.get(i) && recipeIng.matches(slotStack)){
                    //Avoids duplicate matching
                    ingMatched.set(i);
                }
            }
        }

        return ingredients.length - ingMatched.cardinality() == Arrays.stream(ingredients).filter(ing -> ing instanceof IngredientUnknown || ing == null).count();
    }

    @Override
    public ArrayList<ItemStack> getIngredients() {
        return (ArrayList<ItemStack>) Arrays.stream(ingredients)
            .map(Utils::getFirstStack)
            .collect(Collectors.toList()
        );
    }
}
