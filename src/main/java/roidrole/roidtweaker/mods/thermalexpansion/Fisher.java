package roidrole.roidtweaker.mods.thermalexpansion;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.managers.device.FisherManager;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.roidtweaker.mixins.thermalexpansion.IFisherManagerAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import roidrole.roidtweaker.utils.actions.ListClear;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenClass("mods.thermalexpansion.Fisher")
@SuppressWarnings("unused")
public class Fisher {
    @ZenMethod
    public static void addFish(IItemStack fish, int weight){
        //Since fish is a list in the base mod, we must add and remove sequentially
        ItemStack fishStack = CraftTweakerMC.getItemStack(fish);
        DeferredLoader.loadComplete(FunctionInvoke.from(FisherManager::addFish, fishStack, weight));
    }
    @ZenMethod
    public static void removeFish(IItemStack fish){
        FishRemoval.getRemove().removedFishes.add(CraftTweakerMC.getItemStack(fish));
    }
    @ZenMethod
    public static void clearFish(){
        DeferredLoader.loadComplete(ListClear.from(IFisherManagerAccessor.getFishList(), "Fisher"));
        DeferredLoader.loadComplete(ListClear.from(IFisherManagerAccessor.getWeightList()));
        DeferredLoader.loadComplete(FunctionInvoke.from(IFisherManagerAccessor::setTotalWeight, 0));
    }

    @ZenMethod
    public static void addBait(IItemStack bait, int multiplier){
        FisherManager.addBait(CraftTweakerMC.getItemStack(bait), multiplier);
    }
    @ZenMethod
    public static void removeBait(IItemStack bait){
        ComparableItemStack stack = new ComparableItemStack(CraftTweakerMC.getItemStack(bait));
        DeferredLoader.loadComplete(ListRemove.from(IFisherManagerAccessor.getBaitMap().keySet(), stack));
    }
    @ZenMethod
    public static void clearBait(){
        DeferredLoader.loadComplete(IFisherManagerAccessor.getBaitMap()::clear);
    }

    public static class FishRemoval implements DeferredLoader.IAction {
        private static FishRemoval instance;


        private static FishRemoval getRemove(){
            if(instance == null){
                instance = new FishRemoval();
                DeferredLoader.loadComplete(instance);
            }
            return instance;
        }

        public List<ItemStack> removedFishes = new ArrayList<>();

        @Override
        public void load() {
            List<ItemStack> fishList = IFisherManagerAccessor.getFishList();

            if(fishList.isEmpty()){
                return;
            }
            //Workaround because List.indexOf and List.contains doesn't work for ItemStacks due to .equals being weird
            BitSet removeSet = new BitSet(fishList.size());
            for (int i = 0; i < fishList.size(); i++) {
                for (int j = 0; j < removedFishes.size(); j++) {
                    if(removeSet.get(j)){continue;}
                    if(OreDictionary.itemMatches(removedFishes.get(j), fishList.get(i), false)){
                        removeSet.set(i);
                        break;
                    }
                }
            }

            int weightToRemove = 0;
            for (int i = removeSet.previousSetBit(removeSet.length()); i != -1; i = removeSet.previousSetBit(i - 1)) { //Loops through the indexes of set bits
                fishList.remove(i);
                weightToRemove += IFisherManagerAccessor.getWeightList().remove(i);
            }

            /* Equivalent code :
            int weightToRemove = 0;
            for (ItemStack removedFish : removedFishes) {
                int i = fishList.indexOf(removedFish);
                if(i == -1){
                    CraftTweakerAPI.logWarning("Couldn't remove fish " + fishList,get(i).toString() + " because it is not in the fish list");
                }
                fishList.remove(i);
                weightToRemove += IFisherManagerAccessor.getWeightList().remove(i);
            }
            */

            IFisherManagerAccessor.setTotalWeight(IFisherManagerAccessor.getTotalWeight() - weightToRemove);
            instance = null;
        }
    }
}
