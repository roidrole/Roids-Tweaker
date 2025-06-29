package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import roidrole.roidtweaker.mods.minecraft.IChunkPos;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IF0RWorld;

@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.F0RWorld")
@ModOnly("f0-resources")
public class F0RWorld {
    public final IF0RWorld internal;

    public F0RWorld(IF0RWorld internal) {
        this.internal = internal;
    }

    @ZenMethod
    public ChunkData getChunkData(IChunkPos pos){
        return new ChunkData(internal.getLoadedChunkData(pos.internal));
    }

    @ZenMethod
    public void setChunkData(IChunkPos pos, ChunkData data){
        internal.loadChunkData(pos.internal, data.internal);
    }

    @ZenMethod
    public void removeChunkData(@Optional IChunkPos pos){
        if(pos == null){
            internal.unloadAllChunkData();
        } else {
            internal.unloadChunkData(pos.internal);
        }
    }

    @ZenMethod
    public boolean hasChunkData(IChunkPos pos){
        return internal.getAllLoadedChunkData().containsKey(pos.internal);
    }
}
