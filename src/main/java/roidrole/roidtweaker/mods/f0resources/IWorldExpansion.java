package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IF0RWorld;

@ModOnly("f0-resources")
@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class IWorldExpansion {
    @ZenMethod
    public static IF0RWorldExpansion getF0RWorld(IWorld world){
        return (IF0RWorldExpansion)IF0RWorld.of(CraftTweakerMC.getWorld(world));
    } //Works
}
