package roidrole.roidtweaker.mods.unidict;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import stanhebben.zenscript.annotations.*;
import wanion.unidict.resource.Resource;
import wanion.unidict.resource.UniResourceContainer;

//A resource type, for example "iron"
@ZenClass("mods.roidtweaker.unidict.IResource")
@ZenRegister
@ModOnly("unidict")
public class CTResource {
	Resource internal;

	public CTResource(Resource internal){
		this.internal = internal;
	}

	@ZenMethod
	@ZenMemberGetter
	@ZenOperator(OperatorType.INDEXGET)
	public IItemStack getStack(int kind){
		return new MCItemStack(internal.getChild(kind).getMainEntry());
	}

	//Returns all stacks registered under "iron" (main stacks only)
	@ZenMethod
	@ZenGetter("stacks")
	public IItemStack[] getStacks(){
		return internal.getChildrenCollection().stream()
			.map(UniResourceContainer::getMainEntry)
			.map(CraftTweakerMC::getIItemStack)
			.toArray(IItemStack[]::new)
		;
	}

	@ZenMethod
	@ZenGetter("name")
	public String getName(){
		return internal.getName();
	}
}
