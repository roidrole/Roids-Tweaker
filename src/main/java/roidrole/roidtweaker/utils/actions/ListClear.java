package roidrole.roidtweaker.utils.actions;

import crafttweaker.CraftTweakerAPI;
import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;
import java.util.Map;

public class ListClear implements DeferredLoader.IAction{
	private final Collection<?> collection;
	private final String recipeType;

	public ListClear(Collection<?> collection, String recipeType) {
		this.collection = collection;
		this.recipeType = recipeType;
	}

	public ListClear(Map<?, ?> map, String recipeType) {
		this.collection = map.entrySet();
		this.recipeType = recipeType;
	}

	@Override
	public void load() {
		this.collection.clear();
		CraftTweakerAPI.logInfo(ListClear.getMessage(recipeType));
	}

	public static String getMessage(String recipeType){
		return "Clearing all" + recipeType + "recipes";
	}
}
