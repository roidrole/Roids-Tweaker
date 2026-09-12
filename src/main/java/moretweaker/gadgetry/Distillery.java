package moretweaker.gadgetry;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import epicsquid.gadgetry.machines.recipe.DistillingRecipe;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.gadgetry.Distillery")
@ZenRegister
@ModOnly("gadgetrymachines")
public class Distillery {
   @ZenMethod
   public static void add(IItemStack output, ILiquidStack fluidOutput, IIngredient input, ILiquidStack fluidInput) {
      final ItemStack out = InputHelper.toStack(output);
      final Object in = Inputs.getObject(input);
      final FluidStack fluidOut = InputHelper.toFluid(fluidOutput);
      final FluidStack fluidIn = InputHelper.toFluid(fluidInput);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            DistillingRecipe recipe = new DistillingRecipe(fluidIn, fluidOut, in, out);
            DistillingRecipe.recipes.add(recipe);
            DistillingRecipe.distillable_items.add(recipe.inputs.get(0));
            DistillingRecipe.distillable_fluids.add(recipe.fluid_in.getFluid());
         }

         public String describe() {
            return "Adds a Distillery-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object obj = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            DistillingRecipe.recipes.removeIf((recipe) -> Inputs.matchesForRemoval(obj, recipe.getOutput()));
         }

         public String describe() {
            return "Removes Distillery-Recipes by Item-Output";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            DistillingRecipe.recipes.clear();
         }

         public String describe() {
            return "Removes all recipes for the distillery";
         }
      });
   }
}
