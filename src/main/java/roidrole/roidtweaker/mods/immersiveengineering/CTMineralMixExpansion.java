package roidrole.roidtweaker.mods.immersiveengineering;

import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.common.util.compat.crafttweaker.Excavator;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.oredict.WeightedOreDictEntry;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import roidrole.roidtweaker.mixins.immersiveengineering.AccMTMineralMix;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenExpansion("mods.immersiveengineering.MineralMix")
@ModOnly("immersiveengineering")
@SuppressWarnings("unused")
public class CTMineralMixExpansion {
	@ZenCaster
	@ZenMethod
	public static String toString(Excavator.MTMineralMix mix) {
		if(mix == null){
			return null;
		}
		return ((AccMTMineralMix)mix).getMix().name;
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
		return ((AccMTMineralMix)mix).getWeight();
	}
	@ZenMethod
	@ZenSetter("weight")
	public static void setWeight(Excavator.MTMineralMix mix, int weight){
		((AccMTMineralMix) mix).setWeight(weight);
		ExcavatorHandler.mineralList.put(((AccMTMineralMix) mix).getMix(), weight);
	}
	@ZenMethod
	@ZenGetter("ores")
	public static WeightedOreDictEntry[] getOres(Excavator.MTMineralMix mix){
		String[] ores = ((AccMTMineralMix)mix).getMix().ores;
		float[] chances = ((AccMTMineralMix)mix).getMix().chances;
		WeightedOreDictEntry[] output = new WeightedOreDictEntry[ores.length];
		for (int i = 0; i < ores.length; i++) {
			output[i] = new WeightedOreDictEntry(new MCOreDictEntry(ores[i]), chances[i]);
		}
		return output;
	}

	@ZenMethod
	@ZenGetter("dimensionWhitelist")
	public static int[] getDimensionWhitelist(Excavator.MTMineralMix mix){
		return ((AccMTMineralMix)mix).getMix().dimensionWhitelist;
	}
	@ZenMethod
	@ZenSetter("dimensionWhitelist")
	public static void setDimensionWhitelist(Excavator.MTMineralMix mix, int[] whitelist){
		((AccMTMineralMix)mix).getMix().dimensionWhitelist = whitelist;
	}

	@ZenMethod
	@ZenGetter("dimensionBlacklist")
	public static int[] getDimensionBlacklist(Excavator.MTMineralMix mix){
		return ((AccMTMineralMix)mix).getMix().dimensionBlacklist;
	}
	@ZenMethod
	@ZenSetter("dimensionBlacklist")
	public static void setDimensionBlacklist(Excavator.MTMineralMix mix, int[] blacklist){
		((AccMTMineralMix)mix).getMix().dimensionBlacklist = blacklist;
	}
}
