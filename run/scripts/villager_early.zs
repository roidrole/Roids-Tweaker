#loader preinit
import mods.roidtweaker.minecraft.villager.Villager;
import mods.roidtweaker.minecraft.villager.IVillagerCareer;
import crafttweaker.util.IRandom;
import crafttweaker.data.IData;
import mods.ctutils.utils.Math;

//To avoid "Potentially Dangerous alternative prefix" logging, set modid to crafttweaker
Villager.addProfession("crafttweaker:marine_biologist", "contenttweaker:textures/entity/villager/marine_biologist.png");
Villager.addCareer("crafttweaker:marine_biologist", "based_one");

//Villager.clearCareers("immersiveengineering:engineer");


Villager.setProfessionAttributor(function(allowedProfessions as string[]){
    return allowedProfessions[0];
});