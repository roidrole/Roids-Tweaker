package moretweaker.railcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.railcraft.CokeOven")
@ZenRegister
@ModOnly("railcraft")
public class CokeOven {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input, ILiquidStack liquid, @Optional(valueLong = 1800L) final int ticks) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart in = Inputs.getPart(input);
      final FluidStack fluid = CraftTweakerMC.getLiquidStack(liquid);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.cokeOven().newRecipe(RailUtil.toInput(in)).output(out).fluid(fluid).time(ticks).name(MoreTweaker.newRL()).register();
         }

         public String describe() {
            return "Adds a CokeOven-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.cokeOven().getRecipes().removeIf((iRecipe) -> Inputs.matchesForRemoval(out, iRecipe.getOutput()));
         }

         public String describe() {
            return "Removes CokeOven-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.cokeOven().getRecipes().clear();
         }

         public String describe() {
            return "Removes all coke oven recipes";
         }
      });
   }
}
