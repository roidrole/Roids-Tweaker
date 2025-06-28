package roidrole.roidtweaker.mods.minecraft;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.util.math.ChunkPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.minecraft.IChunkPos")
public class IChunkPos {
    @ZenMethod
    public static ChunkPos getChunkPos(int x, int z){
        return new ChunkPos(x, z);
    }

    @ZenMethod
    public static ChunkPos getChunkPos(IBlockPos pos){
        return new ChunkPos(CraftTweakerMC.getBlockPos(pos));
    }
}
