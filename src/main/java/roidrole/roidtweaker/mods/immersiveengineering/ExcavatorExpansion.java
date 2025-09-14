package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.Excavator;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ModOnly("immersiveengineering")
@ZenExpansion("mods.immersiveengineering.Excavator")
@SuppressWarnings("unused")
public class ExcavatorExpansion {
	@ZenMethodStatic
	public static List<Excavator.MTMineralMix> getRegisteredMinerals() {
		ArrayList<Excavator.MTMineralMix> output = new ArrayList<>(ExcavatorHandler.mineralList.size());
		ExcavatorHandler.mineralList.forEach(
			(mix, weight) -> output.add(new Excavator.MTMineralMix(mix, weight))
		);
		return output;
	}
}
