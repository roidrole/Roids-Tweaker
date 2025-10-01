package roidrole.roidtweaker.mods.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientOr;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("any[]")
@ZenRegister
@SuppressWarnings("unused")
public class ArrayExpansion {
	@ZenMethod
	@ZenCaster
	public static IIngredient asIIngredient(IItemStack[] instance){
		return new IngredientOr(instance);
	}
}