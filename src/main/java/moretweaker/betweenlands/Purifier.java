package moretweaker.betweenlands;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import java.util.ArrayList;
import java.util.List;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.api.IBetweenlandsAPI;
import thebetweenlands.api.recipes.IPurifierRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.purifier.PurifierRecipeAspectVial;
import thebetweenlands.common.recipe.purifier.PurifierRecipeStandard;
import thebetweenlands.common.registries.ItemRegistry;

@ZenClass("moretweaker.betweenlands.Purifier")
@ZenRegister
@ModOnly("thebetweenlands")
public class Purifier {
   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output) {
      final ItemStack out = InputHelper.toStack(output);
      final CraftingPart in = Inputs.getPart(input);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               BetweenlandsAPI.getInstance().registerPurifierRecipe(new PurifierRecipeStandard(out, stack));
            }

         }

         public String describe() {
            return "Adds some betweenlands Purifier recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IPurifierRecipe> recipes = new ArrayList();
            BetweenlandsAPI.getInstance().getPurifierRecipes().forEach((r) -> {
               if (PurifierRecipeStandard.class.isAssignableFrom(r.getClass()) && Inputs.matchesForRemoval(out, r.getOutput(Purifier.guessInput(r)))) {
                  recipes.add(r);
               }

            });
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            recipes.forEach(var10001::unregisterPurifierRecipe);
         }

         public String describe() {
            return "Removes some betweenlands Purifier recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IPurifierRecipe> allRecipes = new ArrayList(BetweenlandsAPI.getInstance().getPurifierRecipes());
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            allRecipes.forEach(var10001::unregisterPurifierRecipe);
         }

         public String describe() {
            return "Removes all recipes for the Purifier";
         }
      });
   }

   private static ItemStack guessInput(IPurifierRecipe recipe) {
      if (recipe instanceof PurifierRecipeStandard) {
         return recipe.getOutput(((PurifierRecipeStandard)recipe).getInput());
      } else {
         return recipe instanceof PurifierRecipeAspectVial ? new ItemStack(ItemRegistry.DENTROTHYST_FLUID_VIAL) : ItemStack.EMPTY;
      }
   }
}
