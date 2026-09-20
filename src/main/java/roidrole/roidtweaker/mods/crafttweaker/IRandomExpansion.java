package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IRandom;
import roidrole.roidtweaker.mixins.crafttweaker.AccMCRandom;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.util.IRandom")
@SuppressWarnings("unused")
public class IRandomExpansion {
    @ZenMethod
    public static double nextGaussian(IRandom random) {
        return ((AccMCRandom)random).getRandom().nextGaussian();
    }
}
