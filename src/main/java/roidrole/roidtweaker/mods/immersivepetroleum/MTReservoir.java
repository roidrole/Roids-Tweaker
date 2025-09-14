package roidrole.roidtweaker.mods.immersivepetroleum;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.Objects;

@ModOnly("immersivepetroleum")
@ZenRegister
@ZenClass("mods.roidtweaker.immersivepetroleum.IReservoir")
@SuppressWarnings("unused")
public class MTReservoir {
	public final PumpjackHandler.OilWorldInfo internal;
	public PumpjackHandler.ReservoirType type;

	public MTReservoir(PumpjackHandler.OilWorldInfo info){
		this.internal = info;
		this.type = info.getType();
	}
	public MTReservoir(PumpjackHandler.ReservoirType type){
		this.internal = new PumpjackHandler.OilWorldInfo();
		internal.overrideType = type;
		this.type = type;
	}

	@ZenMethod
	@ZenGetter("capacity")
	public int getCapacity(){
		return internal.capacity;
	}

	@ZenMethod
	@ZenGetter("current")
	public int getCurrent(){
		return internal.current;
	}
	
	@ZenMethod
	@ZenSetter("capacity")
	public void setCapacity(int capacity){
		internal.capacity = capacity;
	}

	@ZenMethod
	@ZenSetter("current")
	public void setCurrent(int current){
		internal.current = current;
	}

	@ZenMethod
	@ZenSetter("type")
	public void setType(String type){
		for(PumpjackHandler.ReservoirType realType : PumpjackHandler.reservoirList.keySet()){
			if(Objects.equals(realType.name, type)){
				this.type = realType;
				this.internal.overrideType = realType;
				return;
			}
		}
		CraftTweakerAPI.logError("No registered reservoir type: "+type);
	}

	@ZenMethod
	@ZenGetter("type")
	public String getType(){
		return type.name;
	}

	@ZenMethod
	@ZenSetter("name")
	public void setName(String name){
		type.name = name;
	}

	@ZenMethod
	@ZenGetter("name")
	public String getName(){
		return type.name;
	}

	@ZenMethod
	@ZenSetter("fluid")
	public void setFluid(String name){
		type.fluid = name;
	}

	@ZenMethod
	@ZenGetter("fluid")
	public String getFluid(){
		return type.fluid;
	}

	@ZenMethod
	@ZenSetter("minSize")
	public void setMinSize(int size){
		type.minSize = size;
	}

	@ZenMethod
	@ZenGetter("minSize")
	public int getMinSize(){
		return type.minSize;
	}

	@ZenMethod
	@ZenSetter("maxSize")
	public void setMaxSize(int size){
		type.maxSize = size;
	}

	@ZenMethod
	@ZenGetter("maxSize")
	public int getMaxSize(){
		return type.maxSize;
	}

	@ZenMethod
	@ZenSetter("replenishRate")
	public void setReplenishRate(int rate){
		type.replenishRate = rate;
	}

	@ZenMethod
	@ZenGetter("replenishRate")
	public int getReplenishRate(){
		return type.replenishRate;
	}

	@ZenMethod
	@ZenSetter("dimensionWhitelist")
	public void setDimensionWhitelist(int[] list){
		type.dimensionWhitelist = list;
	}

	@ZenMethod
	@ZenGetter("dimensionWhitelist")
	public int[] getDimensionWhitelist(){
		return type.dimensionWhitelist;
	}

	@ZenMethod
	@ZenSetter("dimensionBlacklist")
	public void setDimensionBlacklist(int[] list){
		type.dimensionBlacklist = list;
	}

	@ZenMethod
	@ZenGetter("dimensionBlacklist")
	public int[] getDimensionBlacklist(){
		return type.dimensionBlacklist;
	}

	@ZenMethod
	@ZenSetter("biomeBlacklist")
	public void setBiomeBlacklist(String[] list){
		type.biomeBlacklist = list;
	}

	@ZenMethod
	@ZenGetter("biomeBlacklist")
	public String[] getBiomeBlacklist(){
		return type.biomeBlacklist;
	}

	@ZenMethod
	@ZenSetter("biomeWhitelist")
	public void setBiomeWhitelist(String[] list){
		type.biomeWhitelist = list;
	}

	@ZenMethod
	@ZenGetter("biomeWhitelist")
	public String[] getBiomeWhitelist(){
		return type.biomeWhitelist;
	}
}
