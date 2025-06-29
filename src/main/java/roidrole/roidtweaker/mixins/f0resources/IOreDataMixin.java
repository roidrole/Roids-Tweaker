package roidrole.roidtweaker.mixins.f0resources;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import roidrole.roidtweaker.mods.f0resources.IOreDataExpansion;
import v0id.api.f0resources.world.IOreData;

@Mixin(IOreData.class)
@Debug
public interface IOreDataMixin extends IOreDataExpansion {
    default IItemStack createOreStack(int amount){
        return CraftTweakerMC.getIItemStack(((IOreData)this).createOreItem(amount));
    }

    default IItemStack getOreStack(){
        return CraftTweakerMC.getIItemStack(new ItemStack(((IOreData)this).getOreItem(), ((IOreData)this).getOreMeta()));
    }

    default void setOreStack(IItemStack item){
        ((IOreData)this).setOreItem((Item)item.getDefinition().getInternal());
        ((IOreData)this).setOreMeta((short) item.getDamage());
    }
}
