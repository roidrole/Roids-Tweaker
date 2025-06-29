package roidrole.roidtweaker.mods.minecraft;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.util.math.ChunkPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.minecraft.IChunkPos")
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
}