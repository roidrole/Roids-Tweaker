package roidrole.roidtweaker.mixins.crafttweaker;

import crafttweaker.mc1120.util.MCRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(MCRandom.class)
public interface IMCRandomAccessor {
    @Accessor(value = "random", remap = false)
    Random getRandom();
}
