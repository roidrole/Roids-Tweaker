package xyz.tcreopargh.ctintegration.cot;

import crafttweaker.annotations.ModsOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

public class BaubleEventHandler {

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.WornTick")
    public interface OnWornTick {
        void handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.Equipped")
    public interface OnEquipped {
        void handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.Unequipped")
    public interface OnUnequipped {
        void handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.CanEquip")
    public interface CanEquip {
        boolean handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.CanUnequip")
    public interface canUnequip {
        boolean handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.WillAutoSync")
    public interface WillAutoSync {
        boolean handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenRegister
    @ModsOnly({"bauble", "contenttweaker"})
    @ZenClass("mods.ctintegration.cot.baubles.GetBaubleType")
    public interface GetBaubleType {
        String handle(IItemStack bauble);
    }
}
