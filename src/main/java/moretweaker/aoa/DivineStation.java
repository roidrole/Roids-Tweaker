package moretweaker.aoa;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.tslat.aoa3.crafting.recipes.UpgradeKitRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.aoa.DivineStation")
@ZenRegister
@ModOnly("aoa3")
public class DivineStation {
   @ZenMethod
   public static void addRecipe(IItemStack input, IItemStack upgradeKit, IItemStack output) {
      final ItemStack in = InputHelper.toStack(input);
      final ItemStack kit = InputHelper.toStack(upgradeKit);
      final ItemStack out = InputHelper.toStack(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeAdd(IRecipe.class, new UpgradeKitRecipe("", in, kit, out));
         }

         public String describe() {
            return "Adds an upgrade kit recipe.";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(IRecipe.class, (r) -> r instanceof UpgradeKitRecipe && Inputs.matchesForRemoval(out, r.getRecipeOutput()));
         }

         public String describe() {
            return "Removes some upgrade kit recipes.";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(IRecipe.class, (r) -> r instanceof UpgradeKitRecipe);
         }

         public String describe() {
            return "Removes all upgrade kit recipes.";
         }
      });
   }
}
