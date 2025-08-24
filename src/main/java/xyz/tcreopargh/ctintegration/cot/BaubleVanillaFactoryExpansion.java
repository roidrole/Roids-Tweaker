package xyz.tcreopargh.ctintegration.cot;

import crafttweaker.annotations.ModsOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("mods.contenttweaker.VanillaFactory")
@ZenRegister
@ModsOnly({"bauble, contenttweaker"})
@SuppressWarnings("unused")
public class BaubleVanillaFactoryExpansion {
    @ZenMethodStatic
    public static BaubleItemRepresentation createBaubleItem(String unlocalizedName) {
        return new BaubleItemRepresentation(unlocalizedName);
    }
}
