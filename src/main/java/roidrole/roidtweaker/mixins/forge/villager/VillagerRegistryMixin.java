package roidrole.roidtweaker.mixins.forge.villager;

import crafttweaker.mc1120.util.MCRandom;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import roidrole.roidtweaker.mods.minecraft.villager.Villager;

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
        entity.setProfession(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(Villager.professionAttributor.apply(new MCRandom(rand), allowedProfessions))));
        ci.cancel();
    }

    @Inject(
        method = "setRandomProfession(Lnet/minecraft/entity/monster/EntityZombieVillager;Ljava/util/Random;)V",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    private static void roidTweaker_setRandomProfession(EntityZombieVillager entity, Random rand, CallbackInfo ci){
        entity.setForgeProfession(ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(Villager.professionAttributor.apply(new MCRandom(rand), allowedProfessions))));
        ci.cancel();
    }
}
