package moretweaker.railcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mods.railcraft.api.crafting.Crafters;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.railcraft.RollingMachine")
@ZenRegister
@ModOnly("railcraft")
@SuppressWarnings("unused")
public class RollingMachine {
   @ZenMethod
   public static void addShaped(IItemStack output, IIngredient[][] inputs, @Optional(valueLong = 100L) final int ticks) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      Object[] in = Inputs.getShapedObjects(inputs);
      final ResourceLocation rl = MoreTweaker.newRL();
      final ShapedOreRecipe recipe = new ShapedOreRecipe(rl, out, in);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.rollingMachine().newRecipe(out).time(ticks).name(rl).recipe(recipe);
         }

         public String describe() {
            return "Adds a Shaped RollingMachine-Recipe";
         }
      });
   }

   @ZenMethod
   public static void addShapeless(IItemStack output, IIngredient[] inputs, @Optional(valueLong = 100L) final int ticks) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      Object[] in = Inputs.getObjects(inputs);
      final ResourceLocation rl = MoreTweaker.newRL();
      final ShapelessOreRecipe recipe = new ShapelessOreRecipe(rl, out, in);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.rollingMachine().newRecipe(out).time(ticks).name(rl).recipe(recipe);
         }

         public String describe() {
            return "Adds a Shapeless RollingMachine-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.rollingMachine().getRecipes().removeIf((iRecipe) -> Inputs.matchesForRemoval(out, iRecipe.getRecipeOutput()));
         }

         public String describe() {
            return "Removes RollingMachine-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            Crafters.rollingMachine().getRecipes().clear();
         }

         public String describe() {
            return "Removes all RolllingMachine recipes";
         }
      });
   }
}
