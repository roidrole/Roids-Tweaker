package moretweaker.gadgetry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.machines.recipe.GrindingRecipe;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.gadgetry.Grinder")
@ZenRegister
@ModOnly("gadgetrymachines")
@SuppressWarnings("unused")
public class Grinder {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final Object in = Inputs.getObject(input);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            GrindingRecipe recipe = new GrindingRecipe(out, in);
            GrindingRecipe.recipes.add(recipe);
            GrindingRecipe.grindables.add(recipe.inputs.get(0));
         }

         public String describe() {
            return "Adds a Grinder-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object obj = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            GrindingRecipe.recipes.removeIf((recipe) -> Inputs.matchesForRemoval(obj, recipe.getOutput()));
         }

         public String describe() {
            return "Removes Grinder-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            GrindingRecipe.recipes.clear();
         }

         public String describe() {
            return "Removes all recipes for the Grinder";
         }
      });
   }
}
