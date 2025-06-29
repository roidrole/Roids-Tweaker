package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IFluidData;
import v0id.api.f0resources.world.IOreData;
import v0id.f0resources.chunk.ChunkData;

@ModOnly("f0-resources")
@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.IChunkData")
public interface IChunkDataExpansion {
    @ZenMethod
    void addOreData(IOreData data);

    @ZenMethod
    void removeOreData(IOreData data);

    @ZenMethod
    int getSize(); //Testing

    @ZenMethod
    int getFluidLength(); //Testing

    @ZenMethod
    IOreData getOreData(int i);

    @ZenMethod
    IFluidData getFluidData(int i);

    @ZenMethod
    void addFluidData(IFluidData data);

    @ZenMethod
    void removeFluidData(IFluidData data);

    @ZenMethod
    static ChunkData createChunkData(){
        return new ChunkData();
    } //Works

}
