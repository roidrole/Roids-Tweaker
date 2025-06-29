package roidrole.roidtweaker.mods.minecraft;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.mc1120.world.MCBlockPos;
import net.minecraft.util.math.ChunkPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.minecraft.IChunkPos")
@SuppressWarnings("unused")
public abstract class IChunkPos{
    public ChunkPos internal;

    @ZenMethod
    public static IChunkPos getChunkPos(int x, int z){
        return new IChunkPos(){{
            internal = new ChunkPos(x, z);
        }};
    }

    @ZenMethod
    public static IChunkPos getChunkPos(IBlockPos pos){
        return new IChunkPos(){{
            internal = new ChunkPos(CraftTweakerMC.getBlockPos(pos));
        }};
    }

    @ZenMethod
    public IBlockPos getBlockPos(int x, int y, int z){
        return new MCBlockPos(internal.getBlock(x, y, z));
    }
}