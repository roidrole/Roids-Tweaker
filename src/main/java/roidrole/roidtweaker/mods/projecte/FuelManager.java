package roidrole.roidtweaker.mods.projecte;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.util.ArrayList;
import java.util.List;

@ModOnly("projecte")
@ZenRegister
@SuppressWarnings("unused")
//Registering under CTI package because other projecte methods are there
@ZenClass(CTIntegration.CT_PACKAGE + "projecte.FuelManager")
public class FuelManager {
    public static List<ItemStack> customFuels = new ArrayList<>(0);
    public static List<SimpleStack> fuelsToRemove = new ArrayList<>(0);
    public static boolean removeDefaults = false;

    @ZenMethod
    public static void addFuel(IItemStack item){
        customFuels.add(CraftTweakerMC.getItemStack(item));
    }

    @ZenMethod
    public static void removeDefaults(){
        removeDefaults = true;
    }

    @ZenMethod
    public static void removeFuel(IItemStack item){
        fuelsToRemove.add(new SimpleStack(CraftTweakerMC.getItemStack(item)));
    }
}
