package roidrole.roidtweaker.mods.f0resources;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.*;
import v0id.api.f0resources.world.IOreData;

@ZenRegister
@ZenClass("mods.roidtweaker.f0resources.OreData")
@ModOnly("f0-resources")
@SuppressWarnings("unused")
public class OreData {
    public final IOreData internal;

    public OreData(IOreData data){
        this.internal = data;
    }

    @ZenMethod
    public static OreData create(IItemStack stack, int amount){
        OreData data = new OreData(new v0id.f0resources.chunk.OreData());
            data.setStack(stack);
            data.setAmount(amount);
        return data;
    }


    @ZenMethod
    public IItemStack getStack(@Optional int amount){
        if(amount == 0){amount = 1;}
        return CraftTweakerMC.getIItemStack(this.internal.createOreItem(amount));
    }

    @ZenMethod
    @ZenSetter
    public void setStack(IItemStack stack){
        this.internal.setOreItem(CraftTweakerMC.getItemStack(stack).getItem());
        this.internal.setOreMeta((short)stack.getDamage());
    }

    @ZenMethod
    @ZenGetter("amount")
    public int getAmount(){
        return this.internal.getOreAmount();
    }
    @ZenMethod
    @ZenSetter("amount")
    public void setAmount(int amount){
        this.internal.setOreAmount(amount);
    }

    @ZenMethod
    @ZenGetter("requiredTier")
    public int getRequiredTier(){
        return this.internal.getTierReq();
    }
    @ZenMethod
    @ZenGetter("requiredTier")
    public void setRequiredTier(int tier){
        this.internal.setTierReq(tier);
    }

    @ZenMethod
    public IItemStack mine(int quantity){
        if(quantity < this.getAmount()){
            quantity = this.getAmount();
            this.setAmount(0);
        } else {
            this.setAmount(this.getAmount() - quantity);
        }
        return this.getStack(quantity);
    }
}
