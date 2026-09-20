package roidrole.roidtweaker.mods.thermalexpansion;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.thermalexpansion.AccCollectorManager;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.actions.FunctionInvoke;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenClass("mods.thermalexpansion.Collector")
@SuppressWarnings("unused")
public class Collector {
    @ZenMethod
    public static void addCatalyst(IItemStack catalyst, int xp, int factor){
        AccCollectorManager.invokeAddCatalyst(CraftTweakerMC.getItemStack(catalyst), xp, factor);
    }

    @ZenMethod
    public static void removeCatalyst(IItemStack catalyst){
        ItemStack stack = CraftTweakerMC.getItemStack(catalyst);
        DeferredLoader.loadComplete(FunctionInvoke.from(AccCollectorManager::invokeRemoveCatalyst, stack));
    }
}
