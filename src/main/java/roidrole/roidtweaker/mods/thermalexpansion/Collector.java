package roidrole.roidtweaker.mods.thermalexpansion;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import roidrole.roidtweaker.mixins.thermalexpansion.ICollectorManagerAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("thermalexpansion")
@ZenRegister
@ZenClass("mods.thermalexpansion.Collector")
@SuppressWarnings("unused")
public class Collector {
    @ZenMethod
    public static void addCatalyst(IItemStack catalyst, int xp, int factor){
        ICollectorManagerAccessor.invokeAddCatalyst(CraftTweakerMC.getItemStack(catalyst), xp, factor);
    }

    @ZenMethod
    public static void removeCatalyst(IItemStack catalyst){
        DeferredLoader.load(EnumLoadStage.LOAD_COMPLETE, () -> ICollectorManagerAccessor.invokeRemoveCatalyst(CraftTweakerMC.getItemStack(catalyst)));
    }
}
