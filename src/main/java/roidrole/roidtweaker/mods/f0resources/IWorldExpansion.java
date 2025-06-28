package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import net.minecraft.util.math.ChunkPos;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IChunkData;
import v0id.api.f0resources.world.IF0RWorld;

import java.util.Map;

@ModOnly("f0resources")
@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class IWorldExpansion {
    IF0RWorld forWorld = null;

    @ZenMethod
    public static void initF0RData(IWorld world){
        ((IWorldExpansion)world).forWorld = IF0RWorld.of(CraftTweakerMC.getWorld(world));
    }

    @ZenMethod
    public Map<ChunkPos, IChunkData> getAllLoadedChunkData(IWorld world) {
        return ((IWorldExpansion)world).forWorld.getAllLoadedChunkData();
    }

    @ZenMethod
    public IChunkData getLoadedChunkData(IWorld world, ChunkPos pos) {
        return ((IWorldExpansion)world).forWorld.getLoadedChunkData(pos);
    }

    @ZenMethod
    public void setLoadedChunkData(IWorld world, ChunkPos pos, IChunkData data) {
        ((IWorldExpansion)world).forWorld.setLoadedChunkData(pos, data);
    }

    @ZenMethod
    public void unloadChunkData(IWorld world, ChunkPos pos) {
        ((IWorldExpansion)world).forWorld.unloadChunkData(pos);
    }

    @ZenMethod
    public void loadChunkData(IWorld world, ChunkPos pos, IChunkData data) {
        ((IWorldExpansion)world).forWorld.loadChunkData(pos, data);
    }

    @ZenMethod
    public void unloadAllChunkData(IWorld world) {
        ((IWorldExpansion)world).forWorld.unloadAllChunkData();
    }
    //World, ChunkPos Converted
    //Still need to convert IChunkData
}
