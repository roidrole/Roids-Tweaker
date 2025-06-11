package roidrole.roidtweaker.mods.minecraft.villager;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.Villager")
public class Villager {

    @ZenMethod
    public void addTrade(String profession, String career, IItemStack buy1, IItemStack sell, int level){
        addTrade(profession, career, buy1, null, sell, level);
    }

    @ZenMethod
    public void addTrade(String profession, int career, IItemStack buy1, IItemStack sell, int level){
        addTrade(profession, career, buy1, null, sell, level);
    }

    @ZenMethod
    public void addTrade(String profession, String career, IItemStack buy1, IItemStack buy2, IItemStack sell, int level){
        VillagerRegistry.VillagerProfession prof = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(profession));
        int currentID = 0;
        while(true){
            if(prof.getCareer(currentID).getName().equalsIgnoreCase(career)){
                addTrade(profession, currentID, buy1, buy2, sell, level);
                return;
            }
            currentID++;
        }
    }

    @ZenMethod
    public void addTrade(String profession, int career, IItemStack buy1, IItemStack buy2, IItemStack sell, int level){
        ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(profession)).getCareer(career).addTrade(level,
            (merchant, recipeList, random) ->
                recipeList.add(new MerchantRecipe(CraftTweakerMC.getItemStack(buy1), CraftTweakerMC.getItemStack(buy2), CraftTweakerMC.getItemStack(sell)))
        );
    }
}
