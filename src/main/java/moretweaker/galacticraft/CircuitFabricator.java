package moretweaker.galacticraft;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import micdoodle8.mods.galacticraft.api.recipe.CircuitFabricatorRecipes;
import moretweaker.Inputs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("moretweaker.galacticraft.CircuitFabricator")
@ZenRegister
@ModOnly("galacticraftcore")
public class CircuitFabricator {
   @ZenMethod
   public static void add(IItemStack output, IIngredient[] inputs) {
      final ItemStack out = InputHelper.toStack(output);
      if (!out.isEmpty()) {
         if (inputs.length != 5) {
            CraftTweakerAPI.logError("CircuitFabricator expected 5 IIngredients but got " + inputs.length + ".");
         } else {
            final List<Object> in = new ArrayList<>(5);

            for(IIngredient ingredient : inputs) {
               in.add(Inputs.getObject(ingredient));
            }

            CraftTweakerAPI.apply(new IAction() {
               public void apply() {
                  CircuitFabricatorRecipes.addRecipe(out, in);
               }

               public String describe() {
                  return "Adds a CircuitFabricator-Recipe";
               }
            });
         }
      }
   }

   @ZenMethod
   public static void remove(IItemStack output) {
      final ItemStack out = InputHelper.toStack(output);
      if (!out.isEmpty()) {
         CraftTweakerAPI.apply(new IAction() {
            public void apply() {
               CircuitFabricatorRecipes.removeRecipe(out);
            }

            public String describe() {
               return "Removes a CircuitFabricator-Recipe";
            }
         });
      }
   }
}
