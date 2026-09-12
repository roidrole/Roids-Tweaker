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
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.voidcraft.common.machina.addons.TERecipeInfuser;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

@ZenClass("moretweaker.voidcraft.VoidInfusion")
@ZenRegister
@ModOnly("voidcraft")
public class VoidInfusion {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input, final int voidicFluid) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final ItemStack[] in = Inputs.getStackList(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in) {
               TERecipeInfuser.InfuserRecipe recipe = new TERecipeInfuser().new InfuserRecipe(
                   new ItemStack[]{stack},
                   out,
                   voidicFluid
               );
               VoidCraftTERecipes.infuser.registerRecipe(recipe);
            }
         }

         public String describe() {
            return "Adds VoidInfusion-Recipes";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.infuser.getList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes VoidInfusion-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.infuser.getList().clear();
         }

         public String describe() {
            return "Removes all VoidInfusion recipes";
         }
      });
   }
}
