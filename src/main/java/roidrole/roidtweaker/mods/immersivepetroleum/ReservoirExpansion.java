package roidrole.roidtweaker.mods.immersivepetroleum;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ModOnly("immersivepetroleum")
@ZenRegister
@ZenExpansion("mods.immersivepetroleum.Reservoir")
@SuppressWarnings("unused")
public class ReservoirExpansion {
	@ZenMethodStatic
	public static List<MTReservoir> getRegisteredReservoirs() {
		ArrayList<MTReservoir> output = new ArrayList<>(PumpjackHandler.reservoirList.size());
		PumpjackHandler.reservoirList.keySet().forEach(reservoir -> output.add(new MTReservoir(reservoir)));
		return output;
	}
	@ZenMethodStatic
	public static MTReservoir getReservoir(String reservoir) {
		for(PumpjackHandler.ReservoirType type : PumpjackHandler.reservoirList.keySet()){
			if(Objects.equals(type.name, reservoir)){
				return new MTReservoir(type);
			}
		}
		CraftTweakerAPI.logError("No registered reservoir type: "+reservoir);
		return null;
	}

}
