package roidrole.roidtweaker.mods.baubles;

import baubles.api.cap.IBaublesItemHandler;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.IterableSimple;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;
import java.util.Iterator;

@ModOnly("baubles")
@ZenRegister
@IterableSimple("crafttweaker.item.IItemStack")
@ZenClass("mods.ctintegration.baubles.IBaubleInventory")
public class CTBaubleInventory implements Iterable<IItemStack> {

    private final IBaublesItemHandler internal;

    public CTBaubleInventory(IBaublesItemHandler handler) {
        this.internal = handler;
    }

    public IBaublesItemHandler getInternal() {
        return internal;
    }

    @ZenMethod
    public boolean isItemValidForSlot(int slot, IItemStack item, IEntityLivingBase living) {
        EntityLivingBase mcLiving = CraftTweakerMC.getEntityLivingBase(living);
        ItemStack mcItemStack = CraftTweakerMC.getItemStack(item);
        return internal.isItemValidForSlot(slot, mcItemStack, mcLiving);
    }

    @ZenMethod
    public boolean isItemValid(int slot, IItemStack item) {
        ItemStack mcItemStack = CraftTweakerMC.getItemStack(item);
        return internal.isItemValid(slot, mcItemStack);
    }

    @ZenMethod
    public IItemStack insertBaublesItem(IItemStack stack) {
        ItemStack nativeStack = CraftTweakerMC.getItemStack(stack);
        for (int i = 0; i < internal.getSlots(); i++) {
            ItemStack slotStack = internal.insertItem(i, nativeStack, false);
            if (slotStack != nativeStack) {
                return CraftTweakerMC.getIItemStack(slotStack);
            }
        }
        return stack;
    }

    @ZenMethod
    @ZenGetter("slotCount")
    public int getSlotCount() {
        return internal.getSlots();
    }

    @ZenMethod
    public IItemStack getStackInSlot(int slot) {
        return CraftTweakerMC.getIItemStack(internal.getStackInSlot(slot));
    }

    @ZenMethod
    public void setStackInSlot(int slot, IItemStack item) {
        internal.setStackInSlot(slot, CraftTweakerMC.getItemStack(item));
    }

    @ZenMethod
    public int isBaubleEquipped(IItemStack bauble){
        ItemStack toCheck = CraftTweakerMC.getItemStack(bauble);
        if(toCheck.isEmpty()){return -1;}
        for (int i = 0; i < internal.getSlots(); i++) {
            ItemStack inSlot = internal.getStackInSlot(i);
            if (!inSlot.isEmpty() && inSlot.getItem() == toCheck.getItem()) {
                return i;
            }
        }
        return -1;
    }

    @Nonnull
    @ZenMethod
    public Iterator<IItemStack> iterator() {
        return new Iterator<IItemStack>() {
            int current = 0;
            final int max = internal.getSlots() - 1;
            @Override
            public boolean hasNext() {
                return current < max;
            }

            @Override
            public IItemStack next() {
                return CraftTweakerMC.getIItemStack(internal.getStackInSlot(current++));
            }
        };
    }
}
