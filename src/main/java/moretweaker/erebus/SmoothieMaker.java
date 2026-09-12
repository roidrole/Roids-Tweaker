package moretweaker.erebus;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import erebus.ModItems;
import erebus.recipes.SmoothieMakerRecipe;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.ArrayList;

@ZenClass("moretweaker.erebus.SmoothieMaker")
@ZenRegister
@ModOnly("erebus")
public class SmoothieMaker {
   private static Field recipeListField = null;

   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient[] inputs, ILiquidStack[] fluids) {
      addRecipe(output, InputHelper.toIItemStack(new ItemStack(ModItems.MATERIALS, 1, 59)), inputs, fluids);
   }

   @ZenMethod
   public static void addRecipe(IItemStack output, IItemStack container, IIngredient[] inputs, ILiquidStack[] fluids) {
      final ItemStack out = InputHelper.toStack(output);
      final ItemStack c = InputHelper.toStack(container);
      final Object[] in = Inputs.getObjects(inputs);

      for(Object obj : in) {
         if (!(obj instanceof ItemStack) && !(obj instanceof String)) {
            CraftTweakerAPI.logError("For SmoothieMaker.addRecipe: Only IItemSTack and IOreDictEntry are allowed.");
            return;
         }
      }

      final FluidStack[] fl = InputHelper.toFluids(fluids);
      if (in.length <= 4 && fl.length <= 4) {
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               SmoothieMakerRecipe.addRecipe(out, c, fl, in);
            }

            public String describe() {
               return "Adds an smoothie maker recipe";
            }
         });
      } else {
         CraftTweakerAPI.logError("For SmoothieMaker.addRecipe: Not more than 4 ingredients and 4 fluids are allowed.");
      }
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            SmoothieMaker.getMutableList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes some smoothie maker recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            SmoothieMaker.getMutableList().clear();
         }

         public String describe() {
            return "Removes all recipes for the smoothie maker";
         }
      });
   }

   private static ArrayList<SmoothieMakerRecipe> getMutableList() {
      try {
         if (recipeListField == null) {
            recipeListField = SmoothieMakerRecipe.class.getDeclaredField("recipes");
            recipeListField.setAccessible(true);
         }

         return (ArrayList<SmoothieMakerRecipe>)recipeListField.get((Object)null);
      } catch (ClassCastException | ReflectiveOperationException e) {
         CraftTweakerAPI.logError("Could not get mutable list for erebus smoothie maker: " + e.getMessage());
         throw new RuntimeException("Could not get mutable list for erebus smoothie maker: " + e.getMessage(), e);
      }
   }
}
