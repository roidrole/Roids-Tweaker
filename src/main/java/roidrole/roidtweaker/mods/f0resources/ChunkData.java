package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import v0id.api.f0resources.world.IChunkData;
import v0id.api.f0resources.world.IFluidData;
import v0id.api.f0resources.world.IOreData;

import java.util.Random;

@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.ChunkData")
@ModOnly("f0-resources")
public class ChunkData {
    public final IChunkData internal;

    public ChunkData(IChunkData internal) {
        this.internal = internal;
    }

    @ZenMethod
    public static ChunkData create(){
        return new ChunkData(new v0id.f0resources.chunk.ChunkData());
    }

    @ZenMethod
    public void addData(OreData data){
        this.internal.addOreData(data.internal);
    }
    @ZenMethod
    public void addData(FluidData data){
        this.internal.addFluidData(data.internal);
    }

    @ZenMethod
    public IOreData getDataOre(@Optional Integer index){
        if(index == null){
            index = new Random().nextInt(this.getSize("ore"));
        }
        return this.internal.getOreData(index);
    }
    @ZenMethod
    public IFluidData getDataFluid(@Optional Integer index){
        if(index == null){
            index = new Random().nextInt(this.getSize("fluid"));
        }
        return this.internal.getFluidData(index);
    }

    @ZenMethod
    public void removeData(OreData data){
        this.internal.removeOreData(data.internal);
    }
    @ZenMethod
    public void removeData(FluidData data){
        this.internal.removeFluidData(data.internal);
    }

    @ZenMethod
    public int getSize(@Optional String type){
        int output = 0;
        //Need to reverse the checks because type may be null and throws an NPE is it is
        if(!"ore".equals(type)){
            output += this.internal.getFluidLength();
        }
        if(!"fluid".equals(type) && !"liquid".equals(type)){
            output += this.internal.getSize();
        }
        return output;
    }
}
