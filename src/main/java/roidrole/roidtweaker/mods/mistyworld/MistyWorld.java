package roidrole.roidtweaker.mods.mistyworld;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import ru.liahim.mist.api.registry.IMistHarvest;
import ru.liahim.mist.api.registry.MistRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.roidtweaker.mistyworld.MistyWorld")
@ModOnly("mist")
@ZenRegister
public class MistyWorld {
	@ZenMethod
	public static void addCompostable(IItemStack stack) {
		MistRegistry.registerCompostIngredient(CraftTweakerMC.getItemStack(stack));
	}

	@ZenMethod
	public static void addHarvestType(IBlockDefinition block, int min, int max) {
		IMistHarvest.HarvestType harvestType = getHarvestType(min, max);
		if(harvestType == null){
			return;
		}
		MistRegistry.registerHarvestType(CraftTweakerMC.getBlock(block), harvestType);
	}

	private static IMistHarvest.HarvestType getHarvestType(int min, int max) {
		if(min == 1){
			if(max == 1){
				return IMistHarvest.HarvestType.WP1_1;
			} else if(max == 2){
				return IMistHarvest.HarvestType.WP1_2;
			} else if(max == 3){
				return IMistHarvest.HarvestType.WP1_3;
			}
		} else if(min == 2){
			if(max == 2){
				return IMistHarvest.HarvestType.WP2_2;
			} else if(max == 3){
				return IMistHarvest.HarvestType.WP2_3;
			}
		} else if(min == 3){
			if(max == 3){
				return IMistHarvest.HarvestType.WP3_3;
			}
		}
		CraftTweakerAPI.logError(
			"Tried to use an invalid HarvestType. HarvestType has to be between 1 and 3 and have max ≥ min. " +
			"Provided: min:"+min+", max:" + max
		);
		return null;
	}
}
