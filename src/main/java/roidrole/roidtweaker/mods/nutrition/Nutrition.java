package roidrole.roidtweaker.mods.nutrition;

import ca.wescook.nutrition.api.INutrient;
import ca.wescook.nutrition.api.NutritionUtil;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.roidtweaker.nutrition.Nutrition")
@ZenRegister
@ModOnly("nutrition")
public class Nutrition {
	@ZenMethod
	public static String[] getNutrientList(){
		List<? extends INutrient> nutrientList = NutritionUtil.getNutrients();
		String[] out = new String[nutrientList.size()];
		for (int i = 0; i < nutrientList.size(); i++) {
			out[i] = nutrientList.get(i).getName();
		}
		return out;
	}
}
