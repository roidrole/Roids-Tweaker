package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.recipe.IntegrationRecipeBasic;
import com.google.common.collect.ImmutableSet;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;

@ZenClass("moretweaker.buildcraft.IntegrationTable")
@ZenRegister
@ModOnly("buildcraftsilicon")
@SuppressWarnings("unused")
public class IntegrationTable {
   @ZenMethod
   public static void add(IItemStack output, long energyCost, IIngredient catalyst, IIngredient[] inputs) {
      ItemStack out = CraftTweakerMC.getItemStack(output);
      ImmutableSet<IngredientStack> in = BuildUtil.getIngredients(inputs);
      IngredientStack c = IngredientStack.of(Inputs.getObject(catalyst));
      final IntegrationRecipe recipe = new IntegrationRecipeBasic(MoreTweaker.newRL(), energyCost * MjAPI.MJ, c, in.asList(), out);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            BuildcraftRecipeRegistry.integrationRecipes.addRecipe(recipe);
         }

         public String describe() {
            return "Adds an IntegrationTable-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient catalyst) {
      final Object out = Inputs.getObject(catalyst);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            Iterator<IntegrationRecipe> itr = BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes().iterator();

            while(itr.hasNext()) {
               IntegrationRecipe recipe = itr.next();
               if (Inputs.matchesForRemoval(out, recipe.getCenterStack().ingredient.getMatchingStacks())) {
                  itr.remove();
               }
            }

         }

         public String describe() {
            return "Removes IntegrationTable-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            Iterator<IntegrationRecipe> itr = BuildcraftRecipeRegistry.integrationRecipes.getAllRecipes().iterator();

            while(itr.hasNext()) {
               itr.next();
               itr.remove();
            }

         }

         public String describe() {
            return "Removes all recipes for the Integration Table";
         }
      });
   }
}
