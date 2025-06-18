package roidrole.roidtweaker.mods.minecraft.villager.actions;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.roidtweaker.mixins.forge.villager.IProfessionAccessor;
import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.ArrayList;

public class CareerOverride implements DeferredLoader.IAction {
    String profession;

    public CareerOverride(String profession){
        this.profession = profession;
    }

    @Override
    public void load() {
        ((IProfessionAccessor) ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(profession))).setCareers(new ArrayList<>(0));
    }
}
