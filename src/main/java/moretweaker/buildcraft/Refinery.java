package moretweaker.buildcraft;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.lib.recipe.RefineryRecipeRegistry;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.buildcraft.Refinery")
@ZenRegister
@ModOnly("buildcraftfactory")
public class Refinery {
   @ZenMethod
   public static void addDistilling(ILiquidStack input, ILiquidStack output, ILiquidStack gasOutput, int energy) {
      FluidStack in = InputHelper.toFluid(input);
      FluidStack out = InputHelper.toFluid(output);
      FluidStack gout = InputHelper.toFluid(gasOutput);
      long mj = (long)energy * MjAPI.MJ;
      final RefineryRecipeRegistry.DistillationRecipe recipe = new RefineryRecipeRegistry.DistillationRecipe(mj, in, gout, out);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().addRecipe(recipe);
         }

         public String describe() {
            return "Adds a BuildCraft distilling recipe";
         }
      });
   }

   @ZenMethod
   public static void removeDistilling(ILiquidStack input) {
      final FluidStack in = InputHelper.toFluid(input);
      if (in != null) {
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().removeRecipes((r) -> in.getFluid() == r.in().getFluid());
            }

            public String describe() {
               return "Removes some BuildCraft distilling recipes";
            }
         });
      }
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            BuildcraftRecipeRegistry.refineryRecipes.getDistillationRegistry().removeRecipes((r) -> true);
         }

         public String describe() {
            return "Removes all recipes for the BC Refinery";
         }
      });
   }
}
