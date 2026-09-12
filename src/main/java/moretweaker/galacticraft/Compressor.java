package moretweaker.galacticraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenClass("moretweaker.galacticraft.Compressor")
@ZenRegister
@ModOnly("galacticraftcore")
@SuppressWarnings("unused")
public class Compressor {
   @ZenMethod
   public static void addShaped(IItemStack output, IItemStack[][] inputs) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      if (!out.isEmpty()) {
         final Object[] in = Inputs.getShapedObjects(inputs);
         CraftTweakerAPI.logInfo(Arrays.toString(in));
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               CompressorRecipes.addRecipe(out, in);
            }

            public String describe() {
               return "Adds a Shaped Compressor-Recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void addShapeless(IItemStack output, IIngredient[] inputs) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      if (!out.isEmpty()) {
         final Object[] in = Inputs.getObjects(inputs);
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               CompressorRecipes.addShapelessRecipe(out, in);
            }

            public String describe() {
               return "Adds a Shapeless Compressor-Recipe";
            }
         });
      }
   }

   @ZenMethod
   public static void remove(IItemStack output) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      if (!out.isEmpty()) {
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               CompressorRecipes.removeRecipe(out);
            }

            public String describe() {
               return "Removes a Compressor-Recipe";
            }
         });
      }
   }
}
