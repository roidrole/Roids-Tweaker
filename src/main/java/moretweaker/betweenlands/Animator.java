package moretweaker.betweenlands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.api.IBetweenlandsAPI;
import thebetweenlands.api.recipes.IAnimatorRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.misc.AnimatorRecipe;

import java.util.ArrayList;
import java.util.List;

@ZenClass("moretweaker.betweenlands.Animator")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Animator {
   @ZenMethod
   public static void addRecipe(IItemStack input, final int fuel, final int life, IItemStack output) {
      final ItemStack in = CraftTweakerMC.getItemStack(input);
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            AnimatorRecipe recipe = new AnimatorRecipe(in, fuel, life, out);
            BetweenlandsAPI.getInstance().registerAnimatorRecipe(recipe);
         }

         public String describe() {
            return "Adds an animator recipe with item output";
         }
      });
   }

   @ZenMethod
   public static void addRecipe(IItemStack input, final int fuel, final int life, String entity) {
      final ItemStack in = CraftTweakerMC.getItemStack(input);
      final ResourceLocation entityLocation = new ResourceLocation(entity);
      final Class<? extends Entity> entityClass = EntityList.getClass(entityLocation);
      if (entityClass == null) {
         CraftTweakerAPI.logError("No entity found for id: " + entity);
      } else {
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               AnimatorRecipe recipe = new AnimatorRecipe(in, fuel, life, entityClass);
               recipe.setRenderEntity(entityLocation);
               BetweenlandsAPI.getInstance().registerAnimatorRecipe(recipe);
            }

            public String describe() {
               return "Adds an animator recipe with entity output";
            }
         });
      }
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IAnimatorRecipe> recipes = new ArrayList<>();
            BetweenlandsAPI.getInstance().getAnimatorRecipes().forEach((r) -> {
               if (Inputs.matchesForRemoval(out, r.getResult(Animator.guessInput(r)))) {
                  recipes.add(r);
               }

            });
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            recipes.forEach(var10001::unregisterAnimatorRecipe);
         }

         public String describe() {
            return "Removes some animator recipes by item output";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(String entityOutput) {
      ResourceLocation entityLocation = new ResourceLocation(entityOutput);
      final Class<? extends Entity> entityClass = EntityList.getClass(entityLocation);
      if (entityClass == null) {
         CraftTweakerAPI.logError("No entity found for id: " + entityOutput);
      } else {
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               List<IAnimatorRecipe> recipes = new ArrayList<>();
               BetweenlandsAPI.getInstance().getAnimatorRecipes().forEach((r) -> {
                  Class<? extends Entity> spawnClass = r.getSpawnEntityClass(Animator.guessInput(r));
                  if (spawnClass != null && entityClass.isAssignableFrom(spawnClass)) {
                     recipes.add(r);
                  }

               });
               IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
               recipes.forEach(var10001::unregisterAnimatorRecipe);
            }

            public String describe() {
               return "Removes some animator recipes by entity output";
            }
         });
      }
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IAnimatorRecipe> allRecipes = new ArrayList<>(BetweenlandsAPI.getInstance().getAnimatorRecipes());
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            allRecipes.forEach(var10001::unregisterAnimatorRecipe);
         }

         public String describe() {
            return "Removes all recipes for the Animator";
         }
      });
   }

   private static ItemStack guessInput(IAnimatorRecipe recipe) {
      return recipe instanceof AnimatorRecipe ? ((AnimatorRecipe)recipe).getInput() : ItemStack.EMPTY;
   }
}
