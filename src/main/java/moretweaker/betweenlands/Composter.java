package moretweaker.betweenlands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.misc.CompostRecipe;

@ZenClass("moretweaker.betweenlands.Composter")
@ZenRegister
@ModOnly("thebetweenlands")
public class Composter {
   @ZenMethod
   public static void addRecipe(IIngredient input) {
      addRecipe(input, 12, 8000);
   }

   @ZenMethod
   public static void addRecipe(IIngredient input, final int compostValue, final int compostTime) {
      final CraftingPart part = Inputs.getPart(input);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack stack : part.toStackList()) {
               BetweenlandsAPI.getInstance().registerCompostBinRecipe(new CompostRecipe(compostValue, compostTime, stack));
            }

         }

         public String describe() {
            return "Adds some betweenlands composter recipes";
         }
      });
   }
}
