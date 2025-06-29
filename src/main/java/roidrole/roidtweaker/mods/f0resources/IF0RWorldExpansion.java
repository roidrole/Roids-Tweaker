package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.math.ChunkPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IChunkData;

import java.util.Map;

@ModOnly("f0-resources")
@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.IF0RWorld")
public interface IF0RWorldExpansion {
    @ZenMethod
    Map<ChunkPos, IChunkData> getAllLoadedChunkData(); //Works

    @ZenMethod
    IChunkData getLoadedChunkData(ChunkPos pos); //Works

    @ZenMethod
    void setLoadedChunkData(ChunkPos pos, IChunkData data);

    @ZenMethod
    void unloadChunkData(ChunkPos pos); //Works

    @ZenMethod
    void loadChunkData(ChunkPos pos, IChunkData data); //Testing

    @ZenMethod
    void unloadAllChunkData(); //Works
}
