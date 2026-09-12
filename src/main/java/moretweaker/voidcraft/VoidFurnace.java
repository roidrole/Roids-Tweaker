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
import tamaized.voidcraft.common.machina.addons.TERecipesBlastFurnace;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

@ZenClass("moretweaker.voidcraft.VoidFurnace")
@ZenRegister
@ModOnly("voidcraft")
public class VoidFurnace {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input1, IIngredient input2, @Optional(valueLong = 500L) final int voidicPower) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final ItemStack[] in1 = Inputs.getStackList(input1);
      final ItemStack[] in2 = Inputs.getStackList(input2);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack1 : in1) {
               for(ItemStack stack2 : in2) {
                  TERecipesBlastFurnace.BlastFurnaceRecipe recipe = new TERecipesBlastFurnace().new BlastFurnaceRecipe(
                      new ItemStack[]{stack1, stack2},
                      out,
                      voidicPower
                  );
                  VoidCraftTERecipes.blastFurnace.registerRecipe(recipe);
               }
            }

         }

         public String describe() {
            return "Adds VoidFurnace-Recipes";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.blastFurnace.getList().removeIf((r) -> Inputs.matchesForRemoval(out, r.getOutput()));
         }

         public String describe() {
            return "Removes VoidFurnace-Recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            VoidCraftTERecipes.blastFurnace.getList().clear();
         }

         public String describe() {
            return "Removes all VoidFurnace recipes";
         }
      });
   }
}
