package moretweaker.fossil;

import com.blamejared.mtlib.helpers.InputHelper;
import com.google.common.collect.Iterables;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.fossil.Sifter")
@ZenRegister
@ModOnly("fossil")
public class Sifter {
   @ZenMethod
   public static void addOutput(IIngredient input, IItemStack output, final double weight) {
      final CraftingPart in = Inputs.getPart(input);
      final ItemStack out = InputHelper.toStack(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               RecipeAnalyzer recipe = FAMachineRecipeRegistry.getSifterRecipeForItem(stack);
               if (recipe == null) {
                  recipe = new RecipeAnalyzer(stack);
                  FAMachineRecipeRegistry.sifterRecipes.add(recipe);
               }

               recipe.addOutput(out.copy(), (float)weight);
            }

         }

         public String describe() {
            return "Adds some sifter recipes";
         }
      });
   }

   @ZenMethod
   public static void removeByInput(IIngredient input) {
      final Object in = Inputs.getObject(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FAMachineRecipeRegistry.sifterRecipes.removeIf((r) -> Inputs.matchesForRemoval(in, r.getInput()));
         }

         public String describe() {
            return "Removes some sifter recipes by input";
         }
      });
   }

   @ZenMethod
   public static void removeByOutput(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Set<RecipeAnalyzer> newRecipes = new HashSet();
            Iterator<RecipeAnalyzer> itr = FAMachineRecipeRegistry.sifterRecipes.iterator();

            while(itr.hasNext()) {
               RecipeAnalyzer recipe = (RecipeAnalyzer)itr.next();
               if (Iterables.tryFind(recipe.getDisplayMap().values(), (s) -> Inputs.matchesForRemoval(out, s)).isPresent()) {
                  itr.remove();
                  RecipeAnalyzer r = new RecipeAnalyzer(recipe.getInput());
                  float weight = 0.0F;
                  boolean outputAdded = false;

                  while(weight <= recipe.getTotalWeight()) {
                     Map.Entry<Float, ItemStack> entry = recipe.getDisplayMap().higherEntry(weight);
                     if (entry == null) {
                        break;
                     }

                     float chance = (Float)entry.getKey() - weight;
                     weight = (Float)entry.getKey();
                     if (!Inputs.matchesForRemoval(out, (ItemStack)entry.getValue())) {
                        r.addOutput((ItemStack)entry.getValue(), chance);
                        outputAdded = true;
                     }
                  }

                  if (outputAdded) {
                     newRecipes.add(r);
                  }
               }
            }

            FAMachineRecipeRegistry.sifterRecipes.addAll(newRecipes);
         }

         public String describe() {
            return "Removes some sifter recipes by output";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FAMachineRecipeRegistry.sifterRecipes.clear();
         }

         public String describe() {
            return "Removes all f&a sifter recipes";
         }
      });
   }
}
