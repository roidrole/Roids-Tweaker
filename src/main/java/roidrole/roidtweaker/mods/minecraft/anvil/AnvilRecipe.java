package roidrole.roidtweaker.mods.minecraft.anvil;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import roidrole.roidtweaker.Utils;

public class AnvilRecipe {
    public IIngredient left = IngredientAny.INSTANCE;
    public IIngredient right = IngredientAny.INSTANCE;
    public Object output = IngredientAny.INSTANCE;
    public int xpCost = -1;
    public int repair = 0;
    public boolean disabled = false;
    public boolean matchByOutput = false;

    public void apply(AnvilUpdateEvent event){
        //Disabled recipe handling
        if(disabled){
            event.setCanceled(true);
            return;
        }

        //Build Recipe
        if(output instanceof Float){
            output = Math.round((float)output * (float)event.getLeft().getMaxDamage());
        }
        //repair -> recipe
        if(output instanceof Integer){
            repair = (Integer) output;
        }
        if(xpCost == -1){
            xpCost = 2;
        }

        //Actually apply recipe
        if(repair != 0){
            output = event.getLeft().copy();
            int toRepair = event.getLeft().getItemDamage();
            int repairAmount = Math.min(Utils.ceilDiv(toRepair, repair), event.getRight().getCount());
            ((ItemStack)output).setItemDamage(Math.max(0, toRepair - repair * repairAmount));

            event.setMaterialCost(repairAmount);
            event.setOutput(((ItemStack)output));
        }else{
            event.setMaterialCost(right.getAmount());
            event.setOutput(((ItemStack)output).copy());
        }
        event.setCanceled(false);
        event.setCost(xpCost);
    }

    public boolean matches(AnvilUpdateEvent event){
        if(matchByOutput){return ((IIngredient)output).matches(CraftTweakerMC.getIItemStack(event.getOutput()));}
        if(repair != 0 && event.getLeft().getItemDamage() == 0){return false;}
        return left.matches(CraftTweakerMC.getIItemStack(event.getLeft())) && right.matches(CraftTweakerMC.getIItemStack(event.getRight()));
    }
    public boolean matches(AnvilRepairEvent event){
        if(matchByOutput){return ((IIngredient)output).matches(CraftTweakerMC.getIItemStack(event.getItemResult()));}
        if(repair != 0 && event.getItemInput().getItemDamage() == 0){return false;}
        return left.matches(CraftTweakerMC.getIItemStack(event.getItemInput())) && right.matches(CraftTweakerMC.getIItemStack(event.getIngredientInput()));
    }
}
