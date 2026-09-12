package moretweaker.gadgetry;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import epicsquid.gadgetry.machines.recipe.CombustionValues;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.gadgetry.CombustionGenerator")
@ZenRegister
@ModOnly("gadgetrymachines")
public class CombustionGenerator {
   @ZenMethod
   public static void add(ILiquidStack liquid, int energy) {
      final Fluid fluid = InputHelper.toFluid(liquid).getFluid();
      final int internal = energy / 1000;
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            CombustionValues.values.put(fluid, internal);
         }

         public String describe() {
            return "Adds a Combustion-Value";
         }
      });
   }

   @ZenMethod
   public static void remove(ILiquidStack liquid) {
      final Fluid fluid = InputHelper.toFluid(liquid).getFluid();
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            CombustionValues.values.remove(fluid);
         }

         public String describe() {
            return "Removes a Combustion-Value";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            CombustionValues.values.clear();
         }

         public String describe() {
            return "Removes all recipes for the combustion generator";
         }
      });
   }
}
