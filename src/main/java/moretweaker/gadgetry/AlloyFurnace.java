package moretweaker.gadgetry;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import epicsquid.gadgetry.core.recipe.AlloyRecipe;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.gadgetry.AlloyFurnace")
@ZenRegister
@ModOnly("gadgetrycore")
@SuppressWarnings("unused")
public class AlloyFurnace {
   @ZenMethod
   public static void add(IItemStack output, IIngredient[] inputs) {
      if (inputs.length > 3) {
         CraftTweakerAPI.logError("Can't add AlloyFurnace-Recipe with more than three inputs.");
      } else {
         final ItemStack out = CraftTweakerMC.getItemStack(output);
         final Object[] in = new Object[3];

         int i;
         for(i = 0; i < inputs.length; ++i) {
            in[i] = Inputs.getObject(inputs[i]);
         }

         while(i < 3) {
            in[i] = ItemStack.EMPTY;
            ++i;
         }

         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               AlloyRecipe.addRecipe(new AlloyRecipe(out, in));
            }

            public String describe() {
               return "Adds an AlloyFurnace-Recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object obj = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            AlloyRecipe.recipes.removeIf((recipe) -> Inputs.matchesForRemoval(obj, recipe.getOutput()));
         }

         public String describe() {
            return "Removes AlloyFurnace-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            AlloyRecipe.recipes.clear();
         }

         public String describe() {
            return "Removes all recipes for the AlloyFurnace";
         }
      });
   }
}
