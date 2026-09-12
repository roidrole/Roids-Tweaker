package moretweaker.enderfuge;

import com.blamejared.mtlib.helpers.InputHelper;
import com.themastergeneral.enderfuge.common.processing.EnderfugeFuel;
import com.themastergeneral.enderfuge.common.processing.EnderfugeRecipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.enderfuge.EnderFuge")
@ZenRegister
@ModOnly("enderfuge")
public class EnderFuge {
   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient input) {
      final ItemStack out = InputHelper.toStack(output);
      final ItemStack[] in = Inputs.getStackList(input);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack inPart : in) {
               EnderfugeRecipes.instance().addSmeltingRecipe(inPart, out, 0.0F);
            }

         }

         public String describe() {
            return "Adds EnderFuge-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            EnderfugeRecipes.instance().getSmeltingList().values().removeIf((r) -> Inputs.matchesForRemoval(out, r));
         }

         public String describe() {
            return "Removes EnderFuge-Recipes";
         }
      });
   }

   @ZenMethod
   public static void addFuel(IIngredient fuel, @Optional(valueLong = 400L) final int burnTicks) {
      final ItemStack[] in = Inputs.getStackList(fuel);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack inPart : in) {
               EnderfugeFuel.instance().addEnderfugeFuel(inPart, burnTicks);
            }

         }

         public String describe() {
            return "Adds EnderFuge-Fuel";
         }
      });
   }

   @ZenMethod
   public static void removeFuel(IIngredient fuel) {
      final Object out = Inputs.getObject(fuel);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            EnderfugeFuel.instance().getSmeltingList().keySet().removeIf((r) -> Inputs.matchesForRemoval(out, r));
         }

         public String describe() {
            return "Removes EnderFuge-Fuel";
         }
      });
   }

   @ZenMethod
   public static void removeAllRecipes() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            EnderfugeRecipes.instance().getSmeltingList().clear();
         }

         public String describe() {
            return "Removes all recipes for the EnderFuge";
         }
      });
   }

   @ZenMethod
   public static void removeAllFuels() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            EnderfugeFuel.instance().getSmeltingList().clear();
         }

         public String describe() {
            return "Removes all fuels for the EnderFuge";
         }
      });
   }
}
