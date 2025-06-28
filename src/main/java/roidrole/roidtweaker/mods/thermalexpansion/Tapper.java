package roidrole.roidtweaker.mods.thermalexpansion;

import cofh.core.util.BlockWrapper;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import roidrole.roidtweaker.mixins.thermalexpansion.ITapperManagerAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
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
        ITapperManagerAccessor.invokeAddFertilizer(CraftTweakerMC.getItemStack(fertilizer), multiplier);
    }
    @ZenMethod
    public static void removeFertilizer(IItemStack fertilizer){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> ITapperManagerAccessor.getFertilizerMap().remove(CraftTweakerMC.getItemStack(fertilizer)));
    }
    @ZenMethod
    public static void removeRecipe(IBlockState log){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> {
            BlockWrapper wrapper = new BlockWrapper(CraftTweakerMC.getBlockState(log));
            ITapperManagerAccessor.getLeafMap().removeAll(wrapper);
            ITapperManagerAccessor.getBlockMap().remove(wrapper);
        });
    }
}
