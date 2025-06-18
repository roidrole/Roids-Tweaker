package roidrole.roidtweaker.utils;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

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
}
