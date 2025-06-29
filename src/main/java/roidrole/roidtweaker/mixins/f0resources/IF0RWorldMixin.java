package roidrole.roidtweaker.mixins.f0resources;

import org.spongepowered.asm.mixin.Mixin;
import roidrole.roidtweaker.mods.f0resources.IF0RWorldExpansion;
import v0id.api.f0resources.world.IF0RWorld;

@Mixin(IF0RWorld.class)
public interface IF0RWorldMixin extends IF0RWorldExpansion {
}
