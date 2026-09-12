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
import thebetweenlands.api.recipes.IDruidAltarRecipe;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.recipe.misc.DruidAltarRecipe;

import java.util.ArrayList;
import java.util.List;

@ZenClass("moretweaker.betweenlands.DruidAltar")
@ZenRegister
@ModOnly("thebetweenlands")
@SuppressWarnings("unused")
public class DruidAltar {
   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient[] inputs) {
      if (inputs.length != 4) {
         CraftTweakerAPI.logError("inputs.length not 4 in DruidAltar#addRecipe");
      }

      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final CraftingPart[] in = Inputs.getParts(inputs);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack in0 : in[0].toStackList()) {
               for(ItemStack in1 : in[1].toStackList()) {
                  for(ItemStack in2 : in[2].toStackList()) {
                     for(ItemStack in3 : in[3].toStackList()) {
                        BetweenlandsAPI.getInstance().registerDruidAltarRecipe(new DruidAltarRecipe(in0, in1, in2, in3, out));
                     }
                  }
               }
            }

         }

         public String describe() {
            return "Adds some DruidAltar recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IDruidAltarRecipe> recipes = new ArrayList<>();
            BetweenlandsAPI.getInstance().getDruidAltarRecipes().forEach((r) -> {
               if (r instanceof DruidAltarRecipe && Inputs.matchesForRemoval(out, ((DruidAltarRecipe)r).getDefaultOutput())) {
                  recipes.add(r);
               }

            });
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            recipes.forEach(var10001::unregisterDruidAltarRecipe);
         }

         public String describe() {
            return "Removes some DruidAltar recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IDruidAltarRecipe> allRecipes = new ArrayList<>(BetweenlandsAPI.getInstance().getDruidAltarRecipes());
            IBetweenlandsAPI var10001 = BetweenlandsAPI.getInstance();
            allRecipes.forEach(var10001::unregisterDruidAltarRecipe);
         }

         public String describe() {
            return "Removes all recipes for the Druid Altar";
         }
      });
   }
}
