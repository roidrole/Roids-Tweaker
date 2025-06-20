#loader preinit
import mods.roidtweaker.minecraft.villager.Villager;
import mods.roidtweaker.minecraft.villager.IVillagerCareer;
import crafttweaker.util.IRandom;
import crafttweaker.data.IData;

Villager.addProfession("contenttweaker:marine_biologist", "contenttweaker:textures/entity/villager/marine_biologist.png");
Villager.addCareer("contenttweaker:marine_biologist", "based_one");

//Villager.clearCareers("immersiveengineering:engineer");


Villager.professionAttributor = function(random as IRandom, allowedProfessions as IData) as string{
    return allowedProfessions[random.nextInt(allowedProfessions.length)] as string;
};