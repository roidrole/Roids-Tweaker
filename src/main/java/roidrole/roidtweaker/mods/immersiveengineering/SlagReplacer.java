package roidrole.roidtweaker.mods.immersiveengineering;


import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import com.google.common.base.Predicates;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.immersiveengineering.IArcFurnaceRecipeAccessor;
import roidrole.roidtweaker.mixins.immersiveengineering.IBlastFurnaceRecipeAccessor;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ModOnly("immersiveengineering")
@ZenRegister
@ZenClass("mods.roidtweaker.immersiveengineering.SlagReplacer")
public class SlagReplacer {
    public static List<SlagFilter> filters = new ArrayList<>();

    @ZenMethod
    @SuppressWarnings("unused")
    public static void setSlag(IItemStack slag, @Optional IIngredient filter, @Optional String filterBy){
        filters.add(new SlagFilter(CraftTweakerMC.getItemStack(slag), filterBy, filter));
    }

    public static class SlagFilter{
        ItemStack slag;
        EnumFilter type = EnumFilter.OUTPUT;
        Predicate<ItemStack> ingredient;

        public SlagFilter(ItemStack slag, String type, IIngredient ingredient){
            this.slag = slag;
            if(type != null){
                this.type = EnumFilter.valueOf(type);
            }
            this.ingredient = ingredient != null?
                Utils.getStackMatcher(ingredient):
                Predicates.alwaysTrue()
            ;
        }
        public boolean matches(BlastFurnaceRecipe recipe){
            if(type == EnumFilter.OUTPUT){
                return ingredient.test(recipe.output);
            }
            if(type == EnumFilter.SLAG){
                return ingredient.test(recipe.slag);
            }
            return false;
        }
        public boolean matches(ArcFurnaceRecipe recipe){
            if(type == EnumFilter.OUTPUT){
                return ingredient.test(recipe.output);
            }
            if(type == EnumFilter.SLAG){
                return ingredient.test(recipe.slag);
            }
            return false;
        }
    }

    public enum EnumFilter {
        OUTPUT,
        SLAG
    }

    public static void postInit(){
        if(filters.isEmpty()){return;}
        BlastFurnaceRecipe.recipeList.forEach(recipe -> filters.stream()
            .filter(filter -> filter.matches(recipe))
            .findFirst()
            .ifPresent(slagFilter -> ((IBlastFurnaceRecipeAccessor) recipe).setSlag(slagFilter.slag)))
        ;
        ArcFurnaceRecipe.recipeList.forEach(recipe -> filters.stream()
            .filter(filter -> filter.matches(recipe))
            .findFirst()
            .ifPresent(slagFilter -> ((IArcFurnaceRecipeAccessor) recipe).setSlag(slagFilter.slag)))
        ;
    }
}
