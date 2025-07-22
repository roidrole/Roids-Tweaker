package xyz.tcreopargh.ctintegration.projecte;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ModOnly("projecte")
@ZenExpansion("crafttweaker.item.IItemStack")
@ZenRegister
@SuppressWarnings("unused")
public class IItemStackExpansionProjectE {

    @ZenGetter("emc")
    @ZenMethod
    public static long getEMC(IItemStack itemStack) {
        return EMCManager.getEMC(itemStack);
    }

    @ZenSetter("emc")
    @ZenMethod
    public static void setEMC(IItemStack itemStack, long value) {
        EMCManager.setEMC(itemStack, value);
    }

    @ZenGetter("emcSellValue")
    @ZenMethod
    public static long getEMCSellValue(IItemStack itemStack) {
        return EMCManager.getEMCSellValue(itemStack);
    }
}
