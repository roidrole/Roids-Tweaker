package roidrole.roidtweaker.mods.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

//IBauble Wrapper
//Should also be used to create custom Baubles (static methods?)
@ZenRegister
@ZenClass("mods.ctintegration.baubles.IBauble")
public class CTBauble {
    @ZenMethod
    public static String getBaubleType(IItemStack item) {
        ItemStack internal = ((ItemStack)item.getInternal());
        if(!(internal.getItem() instanceof IBauble)){
            return null;
        }
        return ((IBauble) internal.getItem()).getBaubleType(internal).toString();
    }

    @ZenMethod
    public static int[] getValidSlots(String type) {
        return BaubleType.valueOf(type).getValidSlots();
    }
}
