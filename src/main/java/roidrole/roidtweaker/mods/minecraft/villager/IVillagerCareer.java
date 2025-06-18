package roidrole.roidtweaker.mods.minecraft.villager;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import roidrole.roidtweaker.mixins.forge.villager.IProfessionAccessor;
import roidrole.roidtweaker.mods.minecraft.villager.actions.ITradeRemoval;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.LoadStageEnum;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

@ZenRegister
@ZenClass("mods.roidtweaker.minecraft.villager.IVillagerCareer")
public class IVillagerCareer implements DeferredLoader.IAction {
    public ResourceLocation profession;
    public int careerI = -1;
    public String careerS;
    public List<List<EntityVillager.ITradeList>> addedTrades = new ArrayList<>(3);
    public List<ITradeRemoval> removedTrades = new ArrayList<>();
    public static Collection<PotionType> potionCollection = ForgeRegistries.POTION_TYPES.getValuesCollection();

    public IVillagerCareer(ResourceLocation profession, int career) {
        this.profession = profession;
        this.careerI = career;
        DeferredLoader.load(this, LoadStageEnum.POST_INIT);
    }

    public IVillagerCareer(ResourceLocation profession, String career) {
        this.profession = profession;
        this.careerS = career;
        DeferredLoader.load(this, LoadStageEnum.POST_INIT);
    }

    //TODO:Meta wildcards?

    @ZenMethod
    public void addTrade(int level, IIngredient sell, IIngredient buy1, @Optional IIngredient buy2){
        while(addedTrades.size() < level){
            addedTrades.add(new ArrayList<>(0));
        }
        addedTrades.get(level - 1).add(new CTVillagerTrade(sell, buy1, buy2, (random, item) -> item));
    }

    @ZenMethod
    public void addTradeForEnchantedItem(int level, IIngredient sell, IIngredient buy1, @Optional IIngredient buy2, @Optional int minLevel, @Optional int maxLevel){
        while(addedTrades.size() < level){
            addedTrades.add(new ArrayList<>(0));
        }
        if(minLevel == 0){
            minLevel = 5;
        }
        if(maxLevel == 0){
            maxLevel = 20;
        }
        int finalMinLevel = minLevel;
        int finalMaxLevel = maxLevel;
        addedTrades.get(level - 1).add(
            new CTVillagerTrade(sell, buy1, buy2, (random, item) -> EnchantmentHelper.addRandomEnchantment(random, item, finalMinLevel+random.nextInt(finalMaxLevel - finalMinLevel), false))
        );
    }

    @ZenMethod
    public void addTradeForPotionItem(int level, IIngredient sell, IIngredient buy1, @Optional IIngredient buy2, @Optional String[] potionKeys){
        while(addedTrades.size() < level){
            addedTrades.add(new ArrayList<>(0));
        }
        addedTrades.get(level - 1).add(new CTVillagerTrade(sell, buy1, buy2, (random, item) -> {
            PotionType potion;
            if(potionKeys != null && potionKeys.length > 0){
                potion = PotionType.getPotionTypeForName(potionKeys[random.nextInt(potionKeys.length)]);
            } else {
                potion = potionCollection.stream().skip(random.nextInt(potionCollection.size())).findFirst().get();
            }
            return PotionUtils.addPotionToItemStack(item, potion);
        }));
    }

    @ZenMethod
    public void removeTrade(@Optional int level, @Optional Integer index){
        if(level == 0){
            this.removedTrades.add(new ITradeRemoval.Clear());
        } else if(index == null){
            //index has to be set to Integer because CT defaults @Optional ints to 0 and the argument for value seems broken, always defaulting to 0.
            this.removedTrades.add(new ITradeRemoval.ClearLevel(level));
        } else{
            this.removedTrades.add(new ITradeRemoval.RemoveTrade(level, index));
        }
    }

    @Override
    public void load() {
        VillagerRegistry.VillagerCareer career;
        if(careerI != -1) {
            career = ((IProfessionAccessor) ForgeRegistries.VILLAGER_PROFESSIONS.getValue(profession)).getCareers().get(this.careerI);
        }else{
            career = ((IProfessionAccessor) ForgeRegistries.VILLAGER_PROFESSIONS.getValue(profession)).getCareers().stream().filter(prof -> prof.getName().equals(careerS)).findAny().get();
        }
        this.removedTrades.forEach(trade -> trade.load(career));
        int level = 0;
        for (List<EntityVillager.ITradeList> trades : addedTrades){
            int thisLevel = ++level;
            trades.forEach(trade -> career.addTrade(thisLevel, trade));
        }
    }

    public static class CTVillagerTrade implements EntityVillager.ITradeList{
        IIngredient sell;
        IIngredient buy1;
        IIngredient buy2;
        BiFunction<Random, ItemStack, ItemStack> getOutput;

        CTVillagerTrade(IIngredient sell, IIngredient buy1, IIngredient buy2, BiFunction<Random, ItemStack, ItemStack> getOutput){
            this.sell = sell;
            this.buy1 = buy1;
            this.buy2 = buy2;
            this.getOutput = getOutput;
        }
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(
                Utils.getRandomStack(buy1, random),
                Utils.getRandomStack(buy2, random),
                this.getOutput.apply(random, Utils.getRandomStack(sell, random))
            ));
        }
    }
}
