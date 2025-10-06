package roidrole.roidtweaker.mods.unidict;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wanion.unidict.UniDict;
import wanion.unidict.api.UniDictAPI;
import wanion.unidict.resource.Resource;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.roidtweaker.unidict.UniDict")
@ZenRegister
@ModOnly("unidict")
public class CTUniDict {
	static UniDictAPI api = UniDict.getAPI();
	//Returns the int kind from the provided name. For example, "gear"
	@ZenMethod
	public static int getKind(String name){
		return Resource.getKindFromName(name);
	}

	//Returns the Resources which have all kinds.
	//For example, iron and gold both have the "ingot" and "ore" kind, so they are both included in the returned list.
	@ZenMethod
	public static List<CTResource> getResources(int[] kind){
		List<Resource> nativeList = api.getResources(kind);
		List<CTResource> out = new ArrayList<>(nativeList.size());
		api.getResources(kind).forEach(resource -> out.add(new CTResource(resource)));
		return out;
	}

	@ZenMethod
	public static CTResource getResource(String name){
		return new CTResource(api.getResource(name));
	}
}
