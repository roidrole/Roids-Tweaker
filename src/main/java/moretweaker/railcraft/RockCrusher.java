package moretweaker.railcraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import mods.railcraft.api.crafting.IOutputEntry;
import mods.railcraft.api.crafting.IRockCrusherCrafter;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.railcraft.RockCrusher")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class RockCrusher {
   @ZenMethod
   public static void add(IIngredient input, IItemStack[] outputs, final float[] chances, @Optional(valueLong = 100L) final int ticks) {
      final ItemStack[] out = CraftTweakerMC.getItemStacks(outputs);
      final CraftingPart in = Inputs.getPart(input);
      if (out.length != chances.length) {
         CraftTweakerAPI.logError("Invalid RockCrusher recipe: outputs and chances have different legths: " + out.length + ", " + chances.length);
      }

      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            IRockCrusherCrafter.IRockCrusherRecipeBuilder builder = Crafters.rockCrusher().makeRecipe(RailUtil.toInput(in));

            for(int i = 0; i < out.length; ++i) {
               builder.addOutput(out[i], chances[i]);
            }

            builder.time(ticks).name(MoreTweaker.newRL()).register();
         }

         public String describe() {
            return "Adds a RockCrusher-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.rockCrusher().getRecipes().removeIf((iRecipe) -> Inputs.matchesForRemoval(out, iRecipe.getOutputs(), IOutputEntry::getOutput));
         }

         public String describe() {
            return "Removes RockCrusher-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.rockCrusher().getRecipes().clear();
         }

         public String describe() {
            return "Removes all RockCrusher recipes";
         }
      });
   }
}
