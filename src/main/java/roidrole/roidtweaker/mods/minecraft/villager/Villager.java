package roidrole.roidtweaker.mods.minecraft.villager;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import roidrole.roidtweaker.RoidTweakerConfig;
import roidrole.roidtweaker.Tags;
import roidrole.roidtweaker.mods.forge.Registries;
import roidrole.roidtweaker.mods.minecraft.villager.actions.CareerAddition;
import roidrole.roidtweaker.mods.minecraft.villager.actions.CareerOverride;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.EnumLoadStage;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenClass("mods.roidtweaker.minecraft.villager.Villager")
@SuppressWarnings("unused")
public class Villager {
    public static List<String> disabledCareers = new ArrayList<>(1);
    public static List<String> removedProfessions = new ArrayList<>(1);
    public static VillagerRegistry.VillagerProfession[] allowedProfessions;

    @ZenMethod
    public static void addProfession(String name, @Optional String texture, @Optional String zombie){
        if(texture == null){
            String textureName;
            if(name.contains(":")){
                textureName = name.split(":")[1];
            } else{
                textureName = name;
            }
            texture = Tags.MOD_ID+":textures/entity/villager/"+textureName+".png";
        }
        if(zombie == null){zombie = texture.substring(0, texture.length() - 4)+"_zombie.png";}
        ForgeRegistries.VILLAGER_PROFESSIONS.register(new VillagerRegistry.VillagerProfession(name, texture, zombie));
    }

    @ZenMethod
    public static void addCareer(String profession, String career){
        DeferredLoader.load(new CareerAddition(profession, career), EnumLoadStage.POST_INIT);
    }

    @ZenMethod
    public static void addCareers(String profession, String[] career){
        DeferredLoader.load(new CareerAddition(profession, career), EnumLoadStage.POST_INIT);
    }

    @ZenMethod
    public static void removeProfession(String name){
        //Minecraft professions are registered too early to target them with the mixin.
        if(name.startsWith("minecraft")){
            if(RoidTweakerConfig.mixinCategory.villagerCategory.replaceProfessionSetter) {
                removedProfessions.add(name);
            } else {
                CraftTweakerAPI.logError("Can't remove vanilla professions without setting replaceProfessionSetter=true!");
            }
        } else {
            Registries.disable(name);
        }
    }

    @ZenMethod
    public static void removeCareer(String career){
        disabledCareers.add(career);
    }

    @ZenMethod
    public static void clearCareers(String profession){
        DeferredLoader.load(new CareerOverride(profession), EnumLoadStage.POST_INIT);
    }

    @ZenMethod
    public static IVillagerCareer getCareer(String profession, int index){
        return new IVillagerCareer(new ResourceLocation(profession), index);
    }

    @ZenMethod
    public static IVillagerCareer getCareer(String profession, String name){
        return new IVillagerCareer(new ResourceLocation(profession), name);
    }

    public static void setAllowedProfessions(){
        allowedProfessions = ForgeRegistries.VILLAGER_PROFESSIONS.getValuesCollection().stream()
            .filter(prof -> !removedProfessions.contains(prof.getRegistryName().toString()))
            .toArray(VillagerRegistry.VillagerProfession[]::new)
        ;
    }
}
