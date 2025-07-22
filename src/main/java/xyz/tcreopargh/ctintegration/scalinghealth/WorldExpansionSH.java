package xyz.tcreopargh.ctintegration.scalinghealth;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ModOnly("scalinghealth")
@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
@SuppressWarnings("unused")
public class WorldExpansionSH {

    @ZenGetter("worldDifficulty")
    @ZenMethod
    public static double getWorldDifficulty(IWorld world) {
        return DifficultyManager.getWorldDifficulty(world);
    }

    @ZenSetter("worldDifficulty")
    @ZenMethod
    public static void setWorldDifficulty(IWorld world, double value) {
        DifficultyManager.setWorldDifficulty(world, value);
    }

    @ZenMethod
    public static double getAreaDifficultyAt(IWorld world, IBlockPos pos) {
        return DifficultyManager.getAreaDifficulty(world, pos);
    }
}
