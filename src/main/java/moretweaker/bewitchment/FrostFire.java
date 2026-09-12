package moretweaker.bewitchment;

import com.bewitchment.api.registry.FrostfireRecipe;
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

@ZenClass("moretweaker.bewitchment.FrostFire")
@ZenRegister
@ModOnly("bewitchment")
public class FrostFire {
   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient input) {
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final Ingredient in = Inputs.getPart(input).ingredient;
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeAdd(FrostfireRecipe.class, new FrostfireRecipe(MoreTweaker.newRL(), in, out));
         }

         public String describe() {
            return "Adds a recipe to the witches frostfire.";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(FrostfireRecipe.class, (r) -> Inputs.matchesForRemoval(out, r.output));
         }

         public String describe() {
            return "Removes some recipes from the witches frostfire.";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(FrostfireRecipe.class, (r) -> true);
         }

         public String describe() {
            return "Removes all recipes from the witches frostfire.";
         }
      });
   }
}
