package roidrole.roidtweaker.mods.minecraft.villager.actions;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CareerAddition implements DeferredLoader.IAction {
    ResourceLocation profession;
    List<String> careers;

    public CareerAddition(String profession, String career) {
        this.profession = new ResourceLocation(profession);
        this.careers = Collections.singletonList(career);
    }

    public CareerAddition(String profession, String[] careers) {
        this.profession = new ResourceLocation(profession);
        this.careers = Arrays.asList(careers);
    }

    @Override
    public void load() {
        VillagerRegistry.VillagerProfession profession = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(this.profession);
        if(profession != null){
            careers.forEach(career -> new VillagerRegistry.VillagerCareer(profession, career));
        } else{
            CraftTweakerAPI.logError("No profession found for "+this.profession);
        }
    }
}
