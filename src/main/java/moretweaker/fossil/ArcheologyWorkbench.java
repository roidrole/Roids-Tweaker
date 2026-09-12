package moretweaker.fossil;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeWorktable;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.fossil.ArcheologyWorkbench")
@ZenRegister
@ModOnly("fossil")
public class ArcheologyWorkbench {
   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output) {
      addRecipe(input, output, InputHelper.toIItemStack(new ItemStack(FAItemRegistry.RELIC_SCRAP)));
   }

   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output, IIngredient fuel) {
      final CraftingPart in = Inputs.getPart(input);
      final ItemStack out = InputHelper.toStack(output);
      final CraftingPart f = Inputs.getPart(fuel);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               for(ItemStack theFuel : f.toStackList()) {
                  FAMachineRecipeRegistry.worktableRecipes.add(new RecipeWorktable(stack, out, theFuel));
               }
            }

         }

         public String describe() {
            return "Adds some ArcheologyWorkbench recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FAMachineRecipeRegistry.worktableRecipes.removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes some ArcheologyWorkbench recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FAMachineRecipeRegistry.worktableRecipes.clear();
         }

         public String describe() {
            return "Removes all f&a workbench recipes";
         }
      });
   }
}
