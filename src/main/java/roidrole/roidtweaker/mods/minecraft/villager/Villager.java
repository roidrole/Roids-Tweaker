package roidrole.roidtweaker.mods.minecraft.villager;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import roidrole.roidtweaker.mixins.forge.IProfessionAccessor;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.Villager")
public class Villager {

    @ZenMethod
    public void addTrade(String profession, String career, int level, IItemStack sell, IItemStack buy1, @Optional IItemStack buy2){
        IProfessionAccessor prof = (IProfessionAccessor)ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(profession));
        prof.getCareers().stream().filter(c -> c.getName().equals(career)).findFirst().ifPresent(
            c -> c.addTrade(level,
                (merchant, recipeList, random) ->
                    recipeList.add(new MerchantRecipe(CraftTweakerMC.getItemStack(buy1), CraftTweakerMC.getItemStack(buy2), CraftTweakerMC.getItemStack(sell))))
        );
    }

    @ZenMethod
    public void addTrade(String profession, int career, int level, IItemStack sell, IItemStack buy1, @Optional IItemStack buy2){
        ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(profession)).getCareer(career).addTrade(level,
            (merchant, recipeList, random) ->
                recipeList.add(new MerchantRecipe(CraftTweakerMC.getItemStack(buy1), CraftTweakerMC.getItemStack(buy2), CraftTweakerMC.getItemStack(sell)))
        );
    }
}
