package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.AssemblyRecipeBasic;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.recipe.AssemblyRecipeRegistry;
import com.google.common.collect.ImmutableSet;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.buildcraft.AssemblyTable")
@ZenRegister
@ModOnly("buildcraftsilicon")
@SuppressWarnings("unused")
public class AssemblyTable {
   @ZenMethod
   public static void add(IItemStack output, long energyCost, IIngredient[] inputs) {
      ItemStack out = CraftTweakerMC.getItemStack(output);
      ImmutableSet<IngredientStack> in = BuildUtil.getIngredients(inputs);
      if (!in.isEmpty()) {
         final AssemblyRecipeBasic recipe = new AssemblyRecipeBasic(MoreTweaker.newRL(), energyCost * MjAPI.MJ, in, out);
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               AssemblyRecipeRegistry.register(recipe);
            }

            public String describe() {
               return "Adds an AssemblyTable-Recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            AssemblyRecipeRegistry.REGISTRY.values().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutputPreviews()));
         }

         public String describe() {
            return "Removes AssemblyTable-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            AssemblyRecipeRegistry.REGISTRY.clear();
         }

         public String describe() {
            return "Removes all recipes for the Assembly Table";
         }
      });
   }
}
