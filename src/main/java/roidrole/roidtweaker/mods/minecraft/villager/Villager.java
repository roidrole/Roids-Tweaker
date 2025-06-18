package roidrole.roidtweaker.mods.minecraft.villager;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import roidrole.roidtweaker.Tags;
import roidrole.roidtweaker.mods.forge.Registries;
import roidrole.roidtweaker.mods.minecraft.villager.actions.CareerAddition;
import roidrole.roidtweaker.mods.minecraft.villager.actions.CareerOverride;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.LoadStageEnum;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

//TODO : custom MerchantRecipe allowing the Random object

@ZenRegister
@ZenClass("mods.roidtweaker.minecraft.villager.Villager")
public class Villager {
    public static List<String> disabledCareers = new ArrayList<>(1);

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
        DeferredLoader.load(new CareerAddition(profession, career), LoadStageEnum.POST_INIT);
    }

    @ZenMethod
    public static void addCareers(String profession, String[] career){
        DeferredLoader.load(new CareerAddition(profession, career), LoadStageEnum.POST_INIT);
    }

    @ZenMethod
    public static void removeProfession(String name){
        //Minecraft professions are registered too early to target them with the mixin.
        //TODO : Overwrite getRandomProfession to remove them (make it configurable) and clear careers. Add redirecting invalid getCareers calls to a dummy career.
        if(name.startsWith("minecraft")){
            CraftTweakerAPI.logError("Can't remove vanilla professions!");
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
        DeferredLoader.load(new CareerOverride(profession), LoadStageEnum.POST_INIT);
    }

    @ZenMethod
    public static IVillagerCareer getCareer(String profession, int index){
        return new IVillagerCareer(new ResourceLocation(profession), index);
    }

    @ZenMethod
    public static IVillagerCareer getCareer(String profession, String name){
        return new IVillagerCareer(new ResourceLocation(profession), name);
    }
}
