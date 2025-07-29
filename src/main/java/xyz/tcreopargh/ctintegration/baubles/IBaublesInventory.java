package xyz.tcreopargh.ctintegration.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.IterableSimple;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ModOnly("baubles")
@ZenRegister
@IterableSimple("crafttweaker.item.IItemStack")
@ZenClass(CTIntegration.CT_PACKAGE + "baubles.IBaubleInventory")
@SuppressWarnings("unused")
public interface IBaublesInventory extends Iterable<IItemStack> {

    Object getInternal();

    @ZenMethod
    boolean isItemValidForSlot(int slot, IItemStack item, IEntityLivingBase living);

    @ZenMethod
    boolean isItemValid(int slot, IItemStack item);

    @ZenMethod
    static String getBaubleType(IItemStack item) {
        ItemStack internal = ((ItemStack)item.getInternal());
        if(!(internal.getItem() instanceof IBauble)){
            return null;
        }
        return ((IBauble) internal.getItem()).getBaubleType(internal).toString();
    }

    @ZenMethod
    static int[] getValidSlots(String type) {
        return BaubleType.valueOf(type).getValidSlots();
    }

    @ZenMethod
    int getSlotCount();

    @ZenMethod
    IItemStack getStackInSlot(int slot);

    @ZenMethod
    void setStackInSlot(int slot, IItemStack item);
}
