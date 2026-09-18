package roidrole.roidtweaker.mods.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.utils.functions.BooleanBiFunction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;
import stanhebben.zenscript.annotations.ZenSetter;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

@ZenClass("mods.ctintegration.baubles.InjectableBauble")
@SuppressWarnings("unused")
public class CTInjectableBauble implements IBauble {
    BaubleType type;
    public CTInjectableBauble(BaubleType type){
        this.type = type;
    }
    public BiConsumer<ItemStack, EntityLivingBase> onWornTick = null;
    public BiConsumer<ItemStack, EntityLivingBase> onEquipped = (stack, entity) -> {};
    public BiConsumer<ItemStack, EntityLivingBase> onUnequipped = (stack, entity) -> {};
    public BooleanBiFunction<ItemStack, EntityLivingBase> canEquip = (stack, entity) -> true;
    public BooleanBiFunction<ItemStack, EntityLivingBase> canUnequip = (stack, entity) -> true;
    public BooleanBiFunction<ItemStack, EntityLivingBase> willAutoSync = (stack, entity) -> false;
    public OnDeathFunction<ItemStack, EntityLivingBase, DeathResult> onDeath = (slotIndex, stack, living) -> DeathResult.DEFAULT;

    @ZenProperty
    public boolean mainHand = true;

    //IBauble

    @Override
    @SuppressWarnings("deprecation")
    public BaubleType getBaubleType(ItemStack stack){
        return type;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        this.onWornTick.accept(itemstack, player);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        this.onEquipped.accept(itemstack, player);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        this.onUnequipped.accept(itemstack, player);
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
    public void setOnWornTick(BiConsumer<IItemStack, IEntityLivingBase> function){
        this.onWornTick = (stack, entity) -> function.accept(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("onEquipped")
    public void setOnEquipped(BiConsumer<IItemStack, IEntityLivingBase> function){
        this.onEquipped = (stack, entity) -> function.accept(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("onUnequipped")
    public void setOnUnequipped(BiConsumer<IItemStack, IEntityLivingBase> function){
        this.onUnequipped = (stack, entity) -> function.accept(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("canEquip")
    public void setCanEquip(BooleanBiFunction<IItemStack, IEntityLivingBase> function){
        this.canEquip = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("canUnequip")
    public void setCanUnequip(BooleanBiFunction<IItemStack, IEntityLivingBase> function){
        this.canUnequip = (stack, entity) -> function.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIEntityLivingBase(entity));
    }
    @ZenMethod
    @ZenSetter("willAutoSync")
    public void setWillAutoSync(BooleanBiFunction<IItemStack, IEntityLivingBase> function){
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
