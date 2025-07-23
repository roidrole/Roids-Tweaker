package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IRandom;
import roidrole.roidtweaker.mixins.crafttweaker.IMCRandomAccessor;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.util.IRandom")
@SuppressWarnings("unused")
public interface IRandomExpansion {
    @ZenMethod
    static double nextGaussian(IRandom random) {
        return ((IMCRandomAccessor)random).getRandom().nextGaussian();
    }
}
