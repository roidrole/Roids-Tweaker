package roidrole.roidtweaker.mixins.f0resources;

import org.spongepowered.asm.mixin.Mixin;
import roidrole.roidtweaker.mods.f0resources.IChunkDataExpansion;
import v0id.api.f0resources.world.IChunkData;

@Mixin(IChunkData.class)
public interface IChunkDataMixin extends IChunkDataExpansion {
}
