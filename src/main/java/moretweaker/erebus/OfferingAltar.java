package moretweaker.erebus;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import erebus.recipes.OfferingAltarRecipe;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;

@ZenClass("moretweaker.erebus.OfferingAltar")
@ZenRegister
@ModOnly("erebus")
public class OfferingAltar {
   private static Field recipeListField = null;

   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient input1, IIngredient input2, IIngredient input3) {
      final ItemStack out = InputHelper.toStack(output);
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
            OfferingAltar.getMutableList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
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
            OfferingAltar.getMutableList().clear();
         }

         public String describe() {
            return "Removes all recipes for the offering altar";
         }
      });
   }

   private static ArrayList<OfferingAltarRecipe> getMutableList() {
      try {
         if (recipeListField == null) {
            recipeListField = OfferingAltarRecipe.class.getDeclaredField("list");
            recipeListField.setAccessible(true);
         }

         return (ArrayList<OfferingAltarRecipe>)recipeListField.get((Object)null);
      } catch (ClassCastException | ReflectiveOperationException e) {
         CraftTweakerAPI.logError("Could not get mutable list for erebus offering altar: " + e.getMessage());
         throw new RuntimeException("Could not get mutable list for erebus offering altar: " + e.getMessage(), e);
      }
   }
}
