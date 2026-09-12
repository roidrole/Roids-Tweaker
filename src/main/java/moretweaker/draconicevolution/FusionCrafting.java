package moretweaker.draconicevolution;

import com.brandon3055.draconicevolution.api.fusioncrafting.FusionRecipeAPI;
import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionRecipe;
import com.brandon3055.draconicevolution.api.fusioncrafting.SimpleFusionRecipe;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.ArrayList;
import java.util.List;

@ZenClass("moretweaker.draconicevolution.FusionCrafting")
@ZenRegister
@ModOnly("draconicevolution")
public class FusionCrafting {
   @ZenProperty
   public static final int BASIC = 0;
   @ZenProperty
   public static final int WYVERN = 1;
   @ZenProperty
   public static final int DRACONIC = 2;
   @ZenProperty
   public static final int CHAOTIC = 3;

   @ZenMethod
   public static void add(IItemStack output, IItemStack catalyst, final int tier, long energyCost, IIngredient[] ingredients) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final ItemStack cl = CraftTweakerMC.getItemStack(catalyst);
      final Object[] in = Inputs.getObjects(ingredients);
      final long energyPerIngredient = energyCost / (long)in.length;
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            FusionRecipeAPI.addRecipe(new SimpleFusionRecipe(out, cl, energyPerIngredient, tier, in));
         }

         public String describe() {
            return "Adds a FusionCrafting-Recipe";
         }
      });
   }

   @ZenMethod
   public static void remove(IIngredient catalyst) {
      final Object obj = Inputs.getObject(catalyst);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            List<IFusionRecipe> removal = new ArrayList<>();

            for(IFusionRecipe recipe : FusionRecipeAPI.getRecipes()) {
               if (Inputs.matchesForRemoval(obj, recipe.getRecipeCatalyst())) {
                  removal.add(recipe);
               }
            }

            for(IFusionRecipe recipe : removal) {
               FusionRecipeAPI.removeRecipe(recipe);
            }

         }

         public String describe() {
            return "Removes FusionCrafting-Recipes by catalyst";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            FusionRecipeAPI.getRecipes().clear();
         }

         public String describe() {
            return "Removes all FusionCrafting recipes";
         }
      });
   }
}
