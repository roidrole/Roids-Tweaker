package moretweaker.quacklib;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import kotlin.Unit;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import therealfarfetchd.quacklib.common.api.recipe.AlloyFurnaceRecipes;

import java.util.ArrayList;
import java.util.List;

@ZenClass("moretweaker.quacklib.AlloyFurnace")
@ZenRegister
@ModOnly("quacklib")
public class AlloyFurnace {
   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient[] inputs) {
      final ItemStack out = InputHelper.toStack(output);
      CraftingPart[] in = Inputs.getParts(inputs);
      final List<IngredientTemplate> templates = new ArrayList<>();

      for(CraftingPart part : in) {
         templates.add(new IngredientTemplate(part));
      }

      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            AlloyFurnaceRecipes.INSTANCE.addRecipe((template) -> {
               template.setOutput(new IngredientTemplate(out));
               template.setInputs(templates);
               return Unit.INSTANCE;
            });
         }

         public String describe() {
            return "Adds a quack alloy furnace recipe";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            AlloyFurnaceRecipes.INSTANCE.getRecipesList().removeIf((pair) -> Inputs.matchesForRemoval(out, pair.getSecond().makeStack()));
         }

         public String describe() {
            return "Removes some quack alloy furnace recipe";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            AlloyFurnaceRecipes.INSTANCE.getRecipesList().clear();
         }

         public String describe() {
            return "Removes all quack alloy furnace recipes";
         }
      });
   }
}
