package moretweaker.bewitchment;

import com.bewitchment.api.registry.DistilleryRecipe;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenClass("moretweaker.bewitchment.Distillery")
@ZenRegister
@ModOnly("bewitchment")
@SuppressWarnings("unused")
public class Distillery {
   @ZenMethod
   public static void addRecipe(IItemStack[] outputs, IIngredient[] inputs) {
      if (outputs.length > 6) {
         CraftTweakerAPI.logError("Recipes for the distillery may not have more than 6 outputs.");
      }

      if (inputs.length > 6) {
         CraftTweakerAPI.logError("Recipes for the distillery may not have more than 6 inputs.");
      }

      if (outputs.length <= 6 && inputs.length <= 6) {
         final List<ItemStack> out = Arrays.asList(CraftTweakerMC.getItemStacks(outputs));
         final List<Ingredient> in = Arrays.stream(Inputs.getParts(inputs)).map((c) -> c.ingredient).collect(Collectors.toList());
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               MoreTweaker.forgeAdd(DistilleryRecipe.class, new DistilleryRecipe(MoreTweaker.newRL(), in, out));
            }

            public String describe() {
               return "Adds a recipe to the witches distillery.";
            }
         });
      }
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(DistilleryRecipe.class, (r) -> Inputs.matchesForRemoval(out, r.output));
         }

         public String describe() {
            return "Removes some recipes from the witches distillery.";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(DistilleryRecipe.class, (r) -> true);
         }

         public String describe() {
            return "Removes all recipes from the witches distillery.";
         }
      });
   }
}
