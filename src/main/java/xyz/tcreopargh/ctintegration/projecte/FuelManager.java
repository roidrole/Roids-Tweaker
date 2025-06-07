//Roidrole addition
package xyz.tcreopargh.ctintegration.projecte;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.Tags;

import java.util.ArrayList;
import java.util.List;

@ModOnly("projecte")
@ZenRegister
@ZenClass(Tags.CT_NAMESPACE + "projecte.FuelManager")
public class FuelManager {
    public static List<ItemStack> customFuels = new ArrayList<>(0);
    public static List<SimpleStack> fuelsToRemove = new ArrayList<>(0);
    public static boolean removeDefaults = false;

    @ZenMethod
    public static void addFuel(IIngredient item){
        customFuels.add(CraftTweakerMC.getItemStack(item));
    }

    @ZenMethod
    public static void removeDefaults(){
        removeDefaults = true;
    }

    @ZenMethod
    public static void removeFuel(IIngredient item){
        fuelsToRemove.add(new SimpleStack(CraftTweakerMC.getItemStack(item)));
    }
}
