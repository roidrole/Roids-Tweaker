package xyz.tcreopargh.ctintegration.cot;

import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("mods.contenttweaker.VanillaFactory")
public class BaubleVanillaFactoryExpansion {
    @ZenMethodStatic
    public static BaubleItemRepresentation createBaubleItem(String unlocalizedName) {
        return new BaubleItemRepresentation(unlocalizedName);
    }
}
