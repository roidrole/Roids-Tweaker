package moretweaker.railcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.railcraft.BlastFurnace")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class BlastFurnace {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input, @Optional(valueLong = 1280L) final int ticks, @Optional final int slag) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart in = Inputs.getPart(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.blastFurnace().newRecipe(RailUtil.toInput(in)).output(out).time(ticks).slagOutput(slag).name(MoreTweaker.newRL()).register();
         }

         public String describe() {
            return "Adds a BlastFurnace-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.blastFurnace().getRecipes().removeIf((iRecipe) -> Inputs.matchesForRemoval(out, iRecipe.getOutput()));
         }

         public String describe() {
            return "Removes BlastFurnace-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.blastFurnace().getRecipes().clear();
         }

         public String describe() {
            return "Removes all BlastFurnace recipes";
         }
      });
   }
}
