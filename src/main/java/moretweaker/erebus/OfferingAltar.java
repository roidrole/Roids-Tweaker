package moretweaker.erebus;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import erebus.recipes.OfferingAltarRecipe;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import roidrole.roidtweaker.mixins.erebus.OfferingAltarRecipeAccessor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.erebus.OfferingAltar")
@ZenRegister
@ModOnly("erebus")
@SuppressWarnings("unused")
public class OfferingAltar {

   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, IIngredient input3) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final Object in1 = Inputs.getObject(input1);
      final Object in2 = Inputs.getObject(input2);
      final Object in3 = Inputs.getObject(input3);
      if ((!(in1 instanceof ItemStack) && !(in1 instanceof String) || !(in2 instanceof ItemStack) && !(in2 instanceof String)) && !(in3 instanceof ItemStack) && !(in3 instanceof String)) {
         CraftTweakerAPI.logError("For OfferingAltar.addRecipe: Only IItemSTack and IOreDictEntry are allowed.");
      } else {
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               OfferingAltarRecipe.addRecipe(out, in1, in2, in3);
            }

            public String describe() {
               return "Adds an offering altar recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            OfferingAltarRecipeAccessor.getList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes some offering altar recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            OfferingAltarRecipeAccessor.getList().clear();
         }

         public String describe() {
            return "Removes all recipes for the offering altar";
         }
      });
   }
}
