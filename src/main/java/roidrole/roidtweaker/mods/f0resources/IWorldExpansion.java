package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IF0RWorld;

@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
@ModOnly("f0-resources")
@SuppressWarnings("unused")
public class IWorldExpansion {
    @ZenMethod
    @ZenGetter("F0RWorld")
    public static F0RWorld getF0RWorld(IWorld world){
        return new F0RWorld(IF0RWorld.of(CraftTweakerMC.getWorld(world)));
    }
}
