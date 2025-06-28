package roidrole.roidtweaker.mods.thermalexpansion;

import cofh.thermalexpansion.util.managers.device.FisherManager;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.roidtweaker.mixins.thermalexpansion.IFisherManagerAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
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
    static FishRemoval remove = new FishRemoval();
    @ZenMethod
    public static void addFish(IItemStack fish, int weight){
        //Since fish is a list in the base mod, we must add and remove sequentially
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE,  () -> FisherManager.addFish(CraftTweakerMC.getItemStack(fish), weight));
    }
    @ZenMethod
    public static void removeFish(IItemStack fish){
        remove.removedFishes.add(CraftTweakerMC.getItemStack(fish));
    }
    @ZenMethod
    public static void clearFish(){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> {
            IFisherManagerAccessor.getFishList().removeIf(item -> true);
            IFisherManagerAccessor.getWeightList().removeIf(integer -> true);
            IFisherManagerAccessor.setTotalWeight(0);
        });
    }

    @ZenMethod
    public static void addBait(IItemStack bait, int multiplier){
        FisherManager.addBait(CraftTweakerMC.getItemStack(bait), multiplier);
    }
    @ZenMethod
    public static void removeBait(IItemStack bait){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> IFisherManagerAccessor.getBaitMap().remove(CraftTweakerMC.getItemStack(bait)));
    }
    @ZenMethod
    public static void clearBait(){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> IFisherManagerAccessor.getBaitMap().clear());
    }

    public static class FishRemoval implements DeferredLoader.IAction {
        public List<ItemStack> removedFishes = new ArrayList<>(1);
        public FishRemoval(){
            DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, this);
        }

        @Override
        public void load() {
            List<ItemStack> fishList = IFisherManagerAccessor.getFishList();

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
        }
    }
}
