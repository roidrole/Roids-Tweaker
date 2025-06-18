package roidrole.roidtweaker.mods.forge;


import crafttweaker.annotations.ZenRegister;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Set;

@ZenRegister
@ZenClass("mods.roidtweaker.forge.Registries")
public class Registries {
    public static Set<ResourceLocation> disabledRegistryKeys = new ObjectOpenHashSet<>();

    @ZenMethod
    public static void disable(String registryName){
        disabledRegistryKeys.add(new ResourceLocation(registryName));
    }
}
