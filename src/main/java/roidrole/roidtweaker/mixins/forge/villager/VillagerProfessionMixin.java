package roidrole.roidtweaker.mixins.forge.villager;

import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static roidrole.roidtweaker.mods.minecraft.villager.Villager.disabledCareers;

@Mixin(VillagerRegistry.VillagerProfession.class)
public abstract class VillagerProfessionMixin {

    @Inject(
        method = "register",
        at = @At(value = "HEAD"),
        cancellable = true,
        remap = false
    )
    void roidTweaker_preventRegister(VillagerRegistry.VillagerCareer career, CallbackInfo ci){
        if(disabledCareers.contains(career.getName())){ci.cancel();}
    }
}
