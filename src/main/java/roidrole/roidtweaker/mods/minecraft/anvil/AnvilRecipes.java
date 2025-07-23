package roidrole.roidtweaker.mods.minecraft.anvil;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import roidrole.roidtweaker.utils.Utils;

public class AnvilRecipes {
    public IIngredient left;
    public IIngredient right;
    public ItemStack output;
    public int xpCost = 2;

    public static class DisabledRecipe extends AnvilRecipes {
        @Override
        public void apply(AnvilUpdateEvent event){
            event.setCanceled(true);
        }
        @Override
        public boolean matches(AnvilRepairEvent event){
            return false;
        }
    }
    public static class DisabledRecipeOutput extends DisabledRecipe {
        IIngredient output;

        @Override
        public boolean matches(AnvilUpdateEvent event){
            return output.matches(CraftTweakerMC.getIItemStack(event.getOutput()));
        }
    }

    public static class Repair extends AnvilRecipes {
        public int repair = 0;
        public float repairRatio = 0;
        @Override
        public boolean matches(AnvilUpdateEvent event){
            if(event.getLeft().getItemDamage() == 0){return false;}
            return super.matches(event);
        }
        @Override
        public boolean matches(AnvilRepairEvent event){
            if(event.getItemInput().getItemDamage() == 0){return false;}
            return super.matches(event);
        }

        @Override
        public void apply(AnvilUpdateEvent event){
            ItemStack output = event.getLeft().copy();
            if(repairRatio != 0){
                repair = Math.round(repairRatio * (float)event.getLeft().getMaxDamage());
            }
            int toRepair = event.getLeft().getItemDamage();
            int repairAmount = Math.min(Utils.ceilDiv(toRepair, repair), event.getRight().getCount());
            output.setItemDamage(Math.max(0, toRepair - repair * repairAmount));

            event.setMaterialCost(repairAmount);
            event.setOutput(output);
            event.setCanceled(false);
            if(xpCost != 0) {
                event.setCost(xpCost);
            } else {
                event.setCost(repairAmount);
            }
        }
    }

    public static class Shapeless extends AnvilRecipes {
        @Override
        public boolean matches(AnvilUpdateEvent event){
            IItemStack leftInput = CraftTweakerMC.getIItemStack(event.getLeft());
            IItemStack rightInput = CraftTweakerMC.getIItemStack(event.getRight());
            return (left.matches(leftInput) && right.matches(rightInput)) || (left.matches(rightInput) && right.matches(leftInput));
        }
        @Override
        public boolean matches(AnvilRepairEvent event){
            IItemStack leftInput = CraftTweakerMC.getIItemStack(event.getItemInput());
            IItemStack rightInput = CraftTweakerMC.getIItemStack(event.getIngredientInput());
            return (left.matches(leftInput) && right.matches(rightInput)) || (left.matches(rightInput) && right.matches(leftInput));
        }
    }

    public void apply(AnvilUpdateEvent event){
        event.setMaterialCost(right.getAmount());
        event.setOutput(output.copy());
        event.setCanceled(false);
        if(xpCost != 0) {
            event.setCost(xpCost);
        } else {
            event.setCost(event.getLeft().getRepairCost() + event.getRight().getRepairCost() + right.getAmount());
        }
    }

    public boolean matches(AnvilUpdateEvent event){
        return left.matches(CraftTweakerMC.getIItemStack(event.getLeft())) && right.matches(CraftTweakerMC.getIItemStack(event.getRight()));
    }
    public boolean matches(AnvilRepairEvent event){
        return left.matches(CraftTweakerMC.getIItemStack(event.getItemInput())) && right.matches(CraftTweakerMC.getIItemStack(event.getIngredientInput()));
    }
}
