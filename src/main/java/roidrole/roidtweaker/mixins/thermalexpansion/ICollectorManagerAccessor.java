package roidrole.roidtweaker.mixins.thermalexpansion;

import cofh.thermalexpansion.util.managers.device.XpCollectorManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(XpCollectorManager.class)
public interface ICollectorManagerAccessor {
    @Invoker(value = "addCatalyst", remap = false)
    static void invokeAddCatalyst(ItemStack catalyst, int xp, int factor){
        throw new AssertionError();
    }
    @Invoker(value = "removeCatalyst", remap = false)
    static void invokeRemoveCatalyst(ItemStack catalyst){
        throw new AssertionError();
    }
}
