package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.Excavator;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.oredict.WeightedOreDictEntry;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import roidrole.roidtweaker.mixins.immersiveengineering.IMTMineralMixAccessor;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenExpansion("mods.immersiveengineering.MineralMix")
@ModOnly("immersiveengineering")
public class CTMineralMixExpansion {
	@ZenCaster
	public static String toString(Excavator.MTMineralMix mix) {
		return ((IMTMineralMixAccessor)mix).getMix().name;
	}
	@ZenMethodStatic
	@Deprecated
	public static void printRegisteredMinerals() {
		StringBuilder builder = new StringBuilder("Registered MineralMixes and their contents: ");
		ExcavatorHandler.mineralList.keySet().forEach(mix -> {
			builder.append("\n");
			builder.append(mix.name);
			builder.append(" - ");
			boolean first = true;
			for(String name : mix.ores){
				if(!first){
					builder.append("/");
				} else {
					first = false;
				}
				builder.append(name);
			}
		});
		CraftTweakerAPI.logInfo(builder.toString());
	}

	@ZenMethod
	@ZenGetter("weight")
	public static int getWeight(Excavator.MTMineralMix mix){
		return ((IMTMineralMixAccessor)mix).getWeight();
	}
	@ZenMethod
	@ZenSetter("weight")
	public static void setWeight(Excavator.MTMineralMix mix, int weight){
		((IMTMineralMixAccessor) mix).setWeight(weight);
		ExcavatorHandler.mineralList.put(((IMTMineralMixAccessor) mix).getMix(), weight);
	}
	@ZenMethod
	@ZenGetter("ores")
	public static WeightedOreDictEntry[] getOres(Excavator.MTMineralMix mix){
		String[] ores = ((IMTMineralMixAccessor)mix).getMix().ores;
		float[] chances = ((IMTMineralMixAccessor)mix).getMix().chances;
		WeightedOreDictEntry[] output = new WeightedOreDictEntry[ores.length];
		for (int i = 0; i < ores.length; i++) {
			output[i] = new WeightedOreDictEntry(new MCOreDictEntry(ores[i]), chances[i]);
		}
		return output;
	}

	@ZenMethod
	@ZenGetter("dimensionWhitelist")
	public static int[] getDimensionWhitelist(Excavator.MTMineralMix mix){
		return ((IMTMineralMixAccessor)mix).getMix().dimensionWhitelist;
	}
	@ZenMethod
	@ZenSetter("dimensionWhitelist")
	public static void setDimensionWhitelist(Excavator.MTMineralMix mix, int[] whitelist){
		((IMTMineralMixAccessor)mix).getMix().dimensionWhitelist = whitelist;
	}

	@ZenMethod
	@ZenGetter("dimensionBlacklist")
	public static int[] getDimensionBlacklist(Excavator.MTMineralMix mix){
		return ((IMTMineralMixAccessor)mix).getMix().dimensionBlacklist;
	}
	@ZenMethod
	@ZenSetter("dimensionBlacklist")
	public static void setDimensionBlacklist(Excavator.MTMineralMix mix, int[] blacklist){
		((IMTMineralMixAccessor)mix).getMix().dimensionBlacklist = blacklist;
	}
}
