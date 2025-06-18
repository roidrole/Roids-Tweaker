package roidrole.roidtweaker.mixins.forge.registry;

import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import roidrole.roidtweaker.mods.forge.Registries;

//Basically taken from StellarCore, adapted to use a CT-accessible Set

@Mixin(ForgeRegistry.class)
public abstract class MixinForgeRegistry {
    @Inject(
        method = "register(Lnet/minecraftforge/registries/IForgeRegistryEntry;)V",
        remap = false,
        at=@At(value="HEAD"),
        cancellable = true
    )
    void roidTweaker_preventRegister(final IForgeRegistryEntry<?> value, CallbackInfo ci){
        if(!Registries.disabledRegistryKeys.isEmpty() && Registries.disabledRegistryKeys.contains(value.getRegistryName())){
            ci.cancel();
        }
    }
}
