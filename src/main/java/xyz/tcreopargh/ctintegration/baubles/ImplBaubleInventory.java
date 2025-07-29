package xyz.tcreopargh.ctintegration.baubles;

import baubles.api.cap.IBaublesItemHandler;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class ImplBaubleInventory implements IBaublesInventory {

    private final IBaublesItemHandler internal;

    public ImplBaubleInventory(IBaublesItemHandler handler) {
        this.internal = handler;
    }

    @Override
    public IBaublesItemHandler getInternal() {
        return internal;
    }

    @Override
    public boolean isItemValidForSlot(int slot, IItemStack item, IEntityLivingBase living) {
        EntityLivingBase mcLiving = CraftTweakerMC.getEntityLivingBase(living);
        ItemStack mcItemStack = CraftTweakerMC.getItemStack(item);
        return internal.isItemValidForSlot(slot, mcItemStack, mcLiving);
    }

    @Override
    public boolean isItemValid(int slot, IItemStack item) {
        ItemStack mcItemStack = CraftTweakerMC.getItemStack(item);
        return internal.isItemValid(slot, mcItemStack);
    }

    @Override
    public int getSlotCount() {
        return internal.getSlots();
    }

    @Override
    public IItemStack getStackInSlot(int slot) {
        return CraftTweakerMC.getIItemStack(internal.getStackInSlot(slot));
    }

    @Override
    public void setStackInSlot(int slot, IItemStack item) {
        internal.setStackInSlot(slot, CraftTweakerMC.getItemStack(item));
    }

    @Nonnull
    @Override
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
