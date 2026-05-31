package roidrole.roidtweaker.mods.nutrition;

import ca.wescook.nutrition.api.NutritionUtil;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.player.IPlayer")
@ModOnly("nutrition")
@ZenRegister
public class IPlayerExpansion {
	@ZenMethod
	public static float getNutrient(IPlayer player, String name){
		return NutritionUtil.getNutrient(
			CraftTweakerMC.getPlayer(player),
			NutritionUtil.getNutrientByName(name)
		);
	}

	@ZenMethod
	public static void setNutrient(IPlayer player, String name, float value){
		NutritionUtil.setNutrient(
			CraftTweakerMC.getPlayer(player),
			NutritionUtil.getNutrientByName(name),
			value
		);
	}

	@ZenMethod
	public static void addNutrient(IPlayer player, String name, float value){
		NutritionUtil.addNutrient(
			CraftTweakerMC.getPlayer(player),
			NutritionUtil.getNutrientByName(name),
			value
		);
	}

	@ZenMethod
	public static void addNutrient(IPlayer player, IItemStack item){
		NutritionUtil.addNutrientsToPlayer(
			CraftTweakerMC.getPlayer(player),
			CraftTweakerMC.getItemStack(item)
		);
	}

	@ZenMethod
	public static void resetNutrient(IPlayer player, String name){
		NutritionUtil.resetNutrient(
			CraftTweakerMC.getPlayer(player),
			NutritionUtil.getNutrientByName(name)
		);
	}
}
