package roidrole.roidtweaker.mods.forge;


import crafttweaker.annotations.ZenRegister;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.util.ResourceLocation;
import roidrole.roidtweaker.RoidTweakerConfig;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Set;

@ZenRegister
@ZenClass("mods.roidtweaker.forge.Registries")
@SuppressWarnings("unused")
public class Registries {
    public static final Set<ResourceLocation> disabledRegistryKeys = new ObjectOpenHashSet<>();

    @ZenMethod
    public static void disable(String registryName){
        if(!RoidTweakerConfig.mixinCategory.allowRemovingRegistries){
            Utils.printMixinWarning("remove a registry item", "allowRemoveRegistry");
        }
        disabledRegistryKeys.add(new ResourceLocation(registryName));
    }
}
