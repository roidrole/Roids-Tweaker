package roidrole.roidtweaker.mods.baubles;

import baubles.api.IBaubleType;
import baubles.api.cap.InjectableBauble;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.FunctionalInterfaces;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;
import stanhebben.zenscript.annotations.ZenSetter;

import javax.annotation.Nullable;

@ZenRegister
@ZenClass("mods.ctintegration.baubles.InjectableBauble")
@ModOnly("baubles")
@SuppressWarnings("unused")
public class CTInjectableBauble extends InjectableBauble {
    public CTInjectableBauble(IBaubleType type) {
        super(type, 0);
    }
    public FunctionalInterfaces.VoidBiFunction<ItemStack, EntityLivingBase> onWornTick = null;
    public FunctionalInterfaces.VoidBiFunction<ItemStack, EntityLivingBase> onEquipped = (stack, entity) -> {};
    public FunctionalInterfaces.VoidBiFunction<ItemStack, EntityLivingBase> onUnequipped = (stack, entity) -> {};
    public FunctionalInterfaces.BooleanBiFunction<ItemStack, EntityLivingBase> canEquip = (stack, entity) -> true;
    public FunctionalInterfaces.BooleanBiFunction<ItemStack, EntityLivingBase> canUnequip = (stack, entity) -> true;
    public FunctionalInterfaces.BooleanBiFunction<ItemStack, EntityLivingBase> willAutoSync = (stack, entity) -> false;
    public OnDeathFunction<ItemStack, EntityLivingBase, DeathResult> onDeath = (slotIndex, stack, living) -> DeathResult.DEFAULT;

    @ZenProperty
    public boolean mainHand = true;

    //IBauble
    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        this.onWornTick.apply(itemstack, player);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        this.onEquipped.apply(itemstack, player);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        this.onUnequipped.apply(itemstack, player);
    }

    @Override
    public boolean canEquip(ItemStack itemstack, @Nullable EntityLivingBase entity) {
        return this.canEquip.apply(itemstack, entity);
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return this.canUnequip.apply(itemstack, player);
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return this.willAutoSync.apply(itemstack, player);
    }

    @Override
    public DeathResult onDeath(int slotIndex, ItemStack stack, EntityLivingBase living) {
        return this.onDeath.apply(slotIndex, stack, living);
    }

    //CT Methods
    @ZenMethod
    @ZenSetter("onWornTick")
    public void setOnWornTick(FunctionalInterfaces.VoidBiFunction<IItemStack, IEntityLivingBase> function){
        this.onWornTick = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("onEquipped")
    public void setOnEquipped(FunctionalInterfaces.VoidBiFunction<IItemStack, IEntityLivingBase> function){
        this.onEquipped = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("onUnequipped")
    public void setOnUnequipped(FunctionalInterfaces.VoidBiFunction<IItemStack, IEntityLivingBase> function){
        this.onUnequipped = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("canEquip")
    public void setCanEquip(FunctionalInterfaces.BooleanBiFunction<IItemStack, IEntityLivingBase> function){
        this.canEquip = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("canUnequip")
    public void setCanUnequip(FunctionalInterfaces.BooleanBiFunction<IItemStack, IEntityLivingBase> function){
        this.canUnequip = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("willAutoSync")
    public void setWillAutoSync(FunctionalInterfaces.BooleanBiFunction<IItemStack, IEntityLivingBase> function){
        this.willAutoSync = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("onDeath")
    public void setOnDeath(OnDeathFunction<IItemStack, IEntityLivingBase, String> function){
        this.onDeath = (slot, stack, entity) -> DeathResult.valueOf(function.apply(slot, CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity)));
    }

    @ZenMethod
    public void register(IItemDefinition item){
        Item nativeItem = CraftTweakerMC.getItem(item);
        if(this.onWornTick == null){
            if(nativeItem instanceof ItemArmor) {
                this.onWornTick = (stack, entity) -> nativeItem.onArmorTick(entity.world, (EntityPlayer) entity, stack);
            } else {
                this.onWornTick = (stack, entity) -> nativeItem.onUpdate(stack, entity.world, entity, 0, mainHand);
            }
        }
        BaubleEventHandler.additionalBaubles.put(nativeItem, this);
    }

    //Utils
    @FunctionalInterface
    public interface OnDeathFunction<ItemType, EntityType, ReturnType>{
        ReturnType apply(int slotIndex, ItemType stack, EntityType living);
    }
}
