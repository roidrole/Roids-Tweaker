package moretweaker.voidcraft;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.voidcraft.common.machina.addons.TERecipesMacerator;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

@ZenClass("moretweaker.voidcraft.VoidMacerator")
@ZenRegister
@ModOnly("voidcraft")
@SuppressWarnings("unused")
public class VoidMacerator {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input, @Optional(valueLong = 200L) final int voidicPower) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final ItemStack[] in = Inputs.getStackList(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in) {
               TERecipesMacerator.MaceratorRecipe recipe = new TERecipesMacerator().new MaceratorRecipe(
                   new ItemStack[]{stack},
                   out,
                   voidicPower
               );
               VoidCraftTERecipes.macerator.registerRecipe(recipe);
            }
         }

         public String describe() {
            return "Adds VoidMacerator-Recipes";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.macerator.getList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes VoidMacerator-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.macerator.getList().clear();
         }

         public String describe() {
            return "Removes all VoidMacerator recipes";
         }
      });
   }
}
