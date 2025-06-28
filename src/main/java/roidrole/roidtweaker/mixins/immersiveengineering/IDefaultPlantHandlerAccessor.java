package roidrole.roidtweaker.mixins.immersiveengineering;

import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.HashSet;

@Mixin(BelljarHandler.DefaultPlantHandler.class)
public interface IDefaultPlantHandlerAccessor {
    @Invoker(value = "getSeedSet", remap = false)
    HashSet<ComparableItemStack> invokeGetSeedSet();
}
