package xyz.tcreopargh.ctintegration.projecte;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.config.CustomEMCParser;
import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.util.List;

@ModOnly("projecte")
@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "projecte.EMCManager")
@SuppressWarnings("unused")
public class EMCManager {

    @ZenMethod
    public static void mapEMC() {
        long start = System.currentTimeMillis();
        CustomEMCParser.init();
        CraftTweakerAPI.logInfo("Starting server-side EMC mapping.");
        EMCMapper.map();
        CraftTweakerAPI.logInfo("Registered " + EMCMapper.emc.size() + " EMC values. (took " + (System.currentTimeMillis() - start) + " ms)");
    }

    //Roidrole addition, recommended over full map
    @ZenMethod
    public static void mapEMC(IItemStack item) {
        //Not worth logging time, takes 0-1 ms on my machine
        EMCMapper.emc.put(
            new SimpleStack(new ResourceLocation(item.getDefinition().getId()), item.getDamage()),
            ProjectEAPI.getEMCProxy().getValue(CraftTweakerMC.getItem(item.getDefinition()))
        );
    }

    @ZenMethod
    public static long getEMC(IItemStack item) {
        ItemStack stack = CraftTweakerMC.getItemStack(item);
        return ProjectEAPI.getEMCProxy().getValue(stack);
    }

    @ZenMethod
    public static long getEMCSellValue(IItemStack item) {
        ItemStack stack = CraftTweakerMC.getItemStack(item);
        return ProjectEAPI.getEMCProxy().getSellValue(stack);
    }

    @ZenMethod
    public static boolean isEMCSet(IItemStack item) {
        ItemStack stack = CraftTweakerMC.getItemStack(item);
        return ProjectEAPI.getEMCProxy().hasValue(stack);
    }

    @ZenMethod
    public static void setEMC(IItemStack item, long value) {
        ItemStack stack = CraftTweakerMC.getItemStack(item);
        ProjectEAPI.getEMCProxy().registerCustomEMC(stack, value);
    }

    @ZenMethod
    public static void setIngredientEMC(IIngredient ingredient, long value) {
        List<IItemStack> items = ingredient.getItems();
        for (IItemStack item : items) {
            ItemStack stack = CraftTweakerMC.getItemStack(item);
            ProjectEAPI.getEMCProxy().registerCustomEMC(stack, value);
        }
    }

}
