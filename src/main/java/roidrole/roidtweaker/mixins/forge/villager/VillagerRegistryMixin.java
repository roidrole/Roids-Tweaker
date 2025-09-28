package roidrole.roidtweaker.mixins.forge.villager;

import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static roidrole.roidtweaker.mods.minecraft.villager.Villager.allowedProfessions;

@Mixin(VillagerRegistry.class)
public abstract class VillagerRegistryMixin {
    @Inject(
        method = "setRandomProfession(Lnet/minecraft/entity/passive/EntityVillager;Ljava/util/Random;)V",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    private static void roidTweaker_setRandomProfession(EntityVillager entity, Random rand, CallbackInfo ci){
        entity.setProfession(allowedProfessions[rand.nextInt(allowedProfessions.length)]);
        ci.cancel();
    }

    @Inject(
        method = "setRandomProfession(Lnet/minecraft/entity/monster/EntityZombieVillager;Ljava/util/Random;)V",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    private static void roidTweaker_setRandomProfession(EntityZombieVillager entity, Random rand, CallbackInfo ci){
        entity.setProfession(VillagerRegistry.getId(allowedProfessions[rand.nextInt(allowedProfessions.length)]));
        ci.cancel();
    }
}
