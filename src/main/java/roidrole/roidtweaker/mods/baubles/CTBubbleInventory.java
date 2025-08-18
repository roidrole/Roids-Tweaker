package roidrole.roidtweaker.mods.baubles;

import baubles.api.BaubleType;
import baubles.api.cap.BaublesContainer;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("baubles")
@ZenRegister
@ZenClass("mods.ctintegration.bubbles.IBubbleInventory")
@SuppressWarnings("unused")
public class CTBubbleInventory extends CTBaubleInventory{
    BaublesContainer internal;
    public CTBubbleInventory(BaublesContainer internal) {
        super(internal);
        this.internal = internal;
    }

    //BaubleType.valueOf() crashes when compiling with Bubbles and playing with Baubles. Parsing a BaubleType argument also fails.
    @ZenMethod
    public void growBaubleSlot(String type, int amount) {
        internal.grow(Enum.valueOf(BaubleType.class, type), amount);
    }
    @ZenMethod
    public void shrinkBaubleSlot(String type, int amount) {
        internal.shrink(Enum.valueOf(BaubleType.class, type), amount);
    }
    @ZenMethod
    public void setBaubleSlot(String type, int amount) {
        internal.set(Enum.valueOf(BaubleType.class, type), amount);
    }

    @ZenMethod
    public void resetBaubleSlots() {
        internal.reset();
    }
}
