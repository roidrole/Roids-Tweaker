package moretweaker.betweenlands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thebetweenlands.api.IBetweenlandsAPI;
import thebetweenlands.api.recipes.IPestleAndMortarRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.mortar.PestleAndMortarRecipe;

import java.util.ArrayList;
import java.util.List;

@ZenClass("moretweaker.betweenlands.Mortar")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class Mortar {
   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart in = Inputs.getPart(input);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               BetweenlandsAPI.getInstance().registerPestleAndMortarRecipe(new PestleAndMortarRecipe(out, stack));
            }

         }

         public String describe() {
            return "Adds some Pestle&Mortar recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IPestleAndMortarRecipe> recipes = new ArrayList<>();
            BetweenlandsAPI.getInstance().getPestleAndMortarRecipes().forEach((r) -> {
               if (Inputs.matchesForRemoval(out, r.getOutput(r.getInputs()))) {
                  recipes.add(r);
               }

            });
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            recipes.forEach(var10001::unregisterPestleAndMortarRecipe);
         }

         public String describe() {
            return "Removes some Pestle&Mortar recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IPestleAndMortarRecipe> allRecipes = new ArrayList<>(BetweenlandsAPI.getInstance().getPestleAndMortarRecipes());
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            allRecipes.forEach(var10001::unregisterPestleAndMortarRecipe);
         }

         public String describe() {
            return "Removes all recipes for Pestle&Mortar";
         }
      });
   }
}
