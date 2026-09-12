package moretweaker.lightningcraft;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.lightningcraft.LightningCrusher")
@ZenRegister
@ModOnly("lightningcraft")
public class LightningCrusher {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input) {
      final ItemStack out = InputHelper.toStack(output);
      final Object in = LUtil.getObject(input);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningCrusherRecipes.instance().addRecipe(out, in);
         }

         public String describe() {
            return "Adds a LightningCrusher Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object obj = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningCrusherRecipes.instance().getRecipeList().removeIf((recipe) -> Inputs.matchesForRemoval(obj, recipe.getOutput()));
         }

         public String describe() {
            return "Removes LightningCrusher Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningCrusherRecipes.instance().getRecipeList().clear();
         }

         public String describe() {
            return "Removes all recipes for the Lightning Crusher";
         }
      });
   }
}
