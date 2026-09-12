package moretweaker.matteroverdrive;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import matteroverdrive.data.recipes.InscriberRecipe;
import matteroverdrive.data.recipes.RecipeManager;
import matteroverdrive.init.MatterOverdriveRecipes;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.List;

@ZenClass("moretweaker.matteroverdrive.Inscriber")
@ZenRegister
@ModOnly("matteroverdrive")
public class Inscriber {
   private static Field f_in1 = null;
   private static Field f_in2 = null;
   private static Field f_out = null;
   private static Field f_energy = null;
   private static Field f_ticks = null;
   private static Field f_recipes = null;

   @ZenMethod
   public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output, final int energy, final int time) {
      final CraftingPart in1 = Inputs.getPart(input1);
      final CraftingPart in2 = Inputs.getPart(input2);
      final ItemStack out = InputHelper.toStack(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack1 : in1.toStackList()) {
               for(ItemStack stack2 : in2.toStackList()) {
                  MatterOverdriveRecipes.INSCRIBER.register(Inscriber.getRecipe(stack1, stack2, out, energy, time));
               }
            }

         }

         public String describe() {
            return "Adds some Inscriber recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Inscriber.getMutableRecipeList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes some Inscriber recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Inscriber.getMutableRecipeList().clear();
         }

         public String describe() {
            return "Removes all inscriber recipes";
         }
      });
   }

   private static InscriberRecipe getRecipe(ItemStack in1, ItemStack in2, ItemStack out, int energy, int ticks) {
      try {
         if (f_in1 == null || f_in2 == null || f_out == null || f_energy == null || f_ticks == null) {
            f_in1 = InscriberRecipe.class.getDeclaredField("main");
            f_in1.setAccessible(true);
            f_in2 = InscriberRecipe.class.getDeclaredField("sec");
            f_in2.setAccessible(true);
            f_out = InscriberRecipe.class.getDeclaredField("output");
            f_out.setAccessible(true);
            f_energy = InscriberRecipe.class.getDeclaredField("energy");
            f_energy.setAccessible(true);
            f_ticks = InscriberRecipe.class.getDeclaredField("time");
            f_ticks.setAccessible(true);
         }

         InscriberRecipe recipe = new InscriberRecipe();
         f_in1.set(recipe, in1);
         f_in2.set(recipe, in2);
         f_out.set(recipe, out);
         f_energy.set(recipe, energy);
         f_ticks.set(recipe, ticks);
         return recipe;
      } catch (ReflectiveOperationException e) {
         throw new IllegalStateException(e);
      }
   }

   private static List<InscriberRecipe> getMutableRecipeList() {
      try {
         if (f_recipes == null) {
            f_recipes = RecipeManager.class.getDeclaredField("recipes");
            f_recipes.setAccessible(true);
         }

         return (List<InscriberRecipe>)f_recipes.get(MatterOverdriveRecipes.INSCRIBER);
      } catch (ReflectiveOperationException e) {
         throw new IllegalStateException(e);
      }
   }
}
