package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("f0-resources")
@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.IOreData")
public interface IOreDataExpansion {

    @ZenMethod
    IItemStack createOreStack(int amount);

    @ZenMethod
    IItemStack getOreStack();

    @ZenMethod
    void setOreStack(IItemStack item);

    @ZenMethod
    int getOreAmount();

    @ZenMethod
    void setOreAmount(int i);

    @ZenMethod
    int getTierReq();

    @ZenMethod
    void setTierReq(int i);
}
