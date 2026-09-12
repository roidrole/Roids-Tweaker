package moretweaker.railcraft;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import java.lang.reflect.Field;
import java.util.Map;
import mods.railcraft.api.fuel.FluidFuelManager;
import moretweaker.MoreTweaker;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.railcraft.FluidFuels")
@ZenRegister
@ModOnly("railcraft")
public class FluidFuels {
   private static Field mapField = null;

   @ZenMethod
   public static void add(ILiquidStack fuel, @Optional(valueLong = 4800L) final int heatPerBucket) {
      final FluidStack fluid = InputHelper.toFluid(fuel);
      fluid.amount = 1000;
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FluidFuelManager.addFuel(fluid, heatPerBucket);
         }

         public String describe() {
            return "Adds a railcraft fluid fuel";
         }
      });
   }

   @ZenMethod
   public static void remove(ILiquidStack fuel) {
      final FluidStack fluid = InputHelper.toFluid(fuel);
      fluid.amount = 1000;
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FluidFuels.getFuelMap().keySet().removeIf((f) -> f.getFluid() == fluid.getFluid());
         }

         public String describe() {
            return "Removes a railcraft fluid fuel";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            FluidFuels.getFuelMap().clear();
         }

         public String describe() {
            return "Removes all recipes";
         }
      });
   }

   private static Map<FluidStack, Integer> getFuelMap() {
      try {
         if (mapField == null) {
            mapField = FluidFuelManager.class.getDeclaredField("boilerFuel");
            mapField.setAccessible(true);
         }

         return (Map)mapField.get((Object)null);
      } catch (ReflectiveOperationException e) {
         throw new RuntimeException(e);
      }
   }
}
