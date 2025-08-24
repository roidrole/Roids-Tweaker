package roidrole.roidtweaker.mods.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ctintegration.baubles.IBauble")
@ModOnly("baubles")
@SuppressWarnings("unused")
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

    @ZenMethod
    public static CTInjectableBauble createBauble(String type){
        return new CTInjectableBauble(BaubleType.valueOf(type));
    }
}
