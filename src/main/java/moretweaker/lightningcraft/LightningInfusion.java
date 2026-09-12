package moretweaker.lightningcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.recipes.LightningInfusionRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.lightningcraft.LightningInfusion")
@ZenRegister
@ModOnly("lightningcraft")
@SuppressWarnings("unused")
public class LightningInfusion {
   @ZenMethod
   public static void add(IItemStack output, IIngredient catalyst, final int cost, IIngredient[] inputs) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final Object cl = LUtil.getObject(catalyst);
      final Object[] in = LUtil.getObjects(inputs);
      if (in.length != 4) {
         CraftTweakerAPI.logError("Lightning-Infusion needs 4 inputs, got " + in.length);
      }

      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningInfusionRecipes.instance().addRecipe(out, cost, cl, in);
         }

         public String describe() {
            return "Adds a LightningInfusion Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object obj = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningInfusionRecipes.instance().getRecipeList().removeIf((recipe) -> Inputs.matchesForRemoval(obj, recipe.getOutput()));
         }

         public String describe() {
            return "Removes LightningInfusion Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            LightningInfusionRecipes.instance().getRecipeList().clear();
         }

         public String describe() {
            return "Removes all recipes for LightningInfusion";
         }
      });
   }
}
