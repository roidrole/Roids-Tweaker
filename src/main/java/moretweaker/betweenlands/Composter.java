package moretweaker.betweenlands;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.misc.CompostRecipe;

@ZenClass("moretweaker.betweenlands.Composter")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Composter {
   @ZenMethod
   public static void addRecipe(IIngredient input) {
      addRecipe(input, 12, 8000);
   }

   @ZenMethod
   public static void addRecipe(IIngredient input, final int compostValue, final int compostTime) {
      ItemStack[] inputs = CraftTweakerMC.getExamples(input);
      for (ItemStack stack : inputs){
         BetweenlandsAPI.getInstance().registerCompostBinRecipe(new CompostRecipe(compostValue, compostTime, stack));
      }
   }
}
