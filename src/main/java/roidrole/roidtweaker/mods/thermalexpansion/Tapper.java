package roidrole.roidtweaker.mods.thermalexpansion;

import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.BlockWrapper;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import roidrole.roidtweaker.mixins.thermalexpansion.AccTapperManager;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.actions.ListRemove;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenClass("mods.thermalexpansion.Tapper")
@SuppressWarnings("unused")
public class Tapper {
    @ZenMethod
    public static void addRecipe(ILiquidStack output, IBlockState log, IBlockState[] leaf){
        for (IBlockState oneLeaf : leaf){
            TapperManager.addLeafMapping(CraftTweakerMC.getBlockState(log), CraftTweakerMC.getBlockState(oneLeaf));
        }
        TapperManager.addBlockStateMapping(CraftTweakerMC.getBlockState(log), CraftTweakerMC.getLiquidStack(output));
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack output, IBlockState log, IBlockState leaf){
        TapperManager.addLeafMapping(CraftTweakerMC.getBlockState(log), CraftTweakerMC.getBlockState(leaf));
        TapperManager.addBlockStateMapping(CraftTweakerMC.getBlockState(log), CraftTweakerMC.getLiquidStack(output));
    }
    @ZenMethod
    public static void addFertilizer(IItemStack fertilizer, int multiplier){
        AccTapperManager.invokeAddFertilizer(CraftTweakerMC.getItemStack(fertilizer), multiplier);
    }
    @ZenMethod
    public static void removeFertilizer(IItemStack fertilizer){
        ComparableItemStack stack = new ComparableItemStack(CraftTweakerMC.getItemStack(fertilizer));
        DeferredLoader.loadComplete(ListRemove.from(AccTapperManager.getFertilizerMap().keySet(), stack));
    }
    @ZenMethod
    public static void removeRecipe(IBlockState log){
        DeferredLoader.loadComplete(() -> {
            BlockWrapper wrapper = new BlockWrapper(CraftTweakerMC.getBlockState(log));
            AccTapperManager.getLeafMap().removeAll(wrapper);
            AccTapperManager.getBlockMap().remove(wrapper);
        });
    }
}
