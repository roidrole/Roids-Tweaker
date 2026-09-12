package moretweaker.voidcraft;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import moretweaker.ReflectionHelper;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tamaized.tammodized.common.tileentity.TamTileEntityRecipeList;
import tamaized.voidcraft.common.machina.addons.TERecipesMacerator;
import tamaized.voidcraft.registry.VoidCraftTERecipes;

@ZenClass("moretweaker.voidcraft.VoidMacerator")
@ZenRegister
@ModOnly("voidcraft")
public class VoidMacerator {
   @ZenMethod
   public static void add(IItemStack output, IIngredient input, @Optional(valueLong = 200L) final int voidicPower) {
      final ItemStack out = InputHelper.toStack(output);
      final ItemStack[] in = Inputs.getStackList(input);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in) {
               VoidCraftTERecipes.macerator.registerRecipe(ReflectionHelper.createNested(TERecipesMacerator.MaceratorRecipe.class, VoidCraftTERecipes.macerator, new Class[]{ItemStack[].class, ItemStack.class, Integer.TYPE}, new ItemStack[]{stack}, out, voidicPower));
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
