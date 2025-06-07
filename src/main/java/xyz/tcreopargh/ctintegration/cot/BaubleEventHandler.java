package xyz.tcreopargh.ctintegration.cot;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import xyz.tcreopargh.ctintegration.CTIntegration;

public class BaubleEventHandler {
    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.WornTick")
    public interface OnWornTick {
        void handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.Equipped")
    public interface OnEquipped {
        void handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.Unequipped")
    public interface OnUnequipped {
        void handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.CanEquip")
    public interface CanEquip {
        boolean handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.CanUnequip")
    public interface canUnequip {
        boolean handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.WillAutoSync")
    public interface WillAutoSync {
        boolean handle(IItemStack bauble, IEntityLivingBase wearer);
    }

    @ZenClass(CTIntegration.CT_PACKAGE + "cot.baubles.GetBaubleType")
    public interface GetBaubleType {
        String handle(IItemStack bauble);
    }
}
