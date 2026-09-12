package moretweaker.fossil;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.fossil.CultureVat")
@ZenRegister
@ModOnly("fossil")
@SuppressWarnings("unused")
public class CultureVat {
   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output) {
      final CraftingPart in = Inputs.getPart(input);
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               FAMachineRecipeRegistry.cultivateRecipes.put(stack, out);
            }

         }

         public String describe() {
            return "Adds some CultureVat recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FAMachineRecipeRegistry.cultivateRecipes.values().removeIf((s) -> Inputs.matchesForRemoval(out, s));
         }

         public String describe() {
            return "Removes some CultureVat recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FAMachineRecipeRegistry.cultivateRecipes.clear();
         }

         public String describe() {
            return "Removes all f&a culture vat recipes";
         }
      });
   }
}
