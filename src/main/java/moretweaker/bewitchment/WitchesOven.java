package moretweaker.bewitchment;

import com.bewitchment.api.registry.OvenRecipe;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.CraftingPart;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.bewitchment.WitchesOven")
@ZenRegister
@ModOnly("bewitchment")
public class WitchesOven {
   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output, IItemStack byproduct, @Optional(valueDouble = (double)1.0F) final float chance, @Optional final boolean requiresJar) {
      final CraftingPart in = Inputs.getPart(input);
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      final ItemStack by = CraftTweakerMC.getItemStack(byproduct);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               MoreTweaker.forgeAdd(OvenRecipe.class, new OvenRecipe(MoreTweaker.newRL(), stack, out, by, chance, requiresJar));
            }

         }

         public String describe() {
            return "Adds a recipe to the witches oven.";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(OvenRecipe.class, (r) -> Inputs.matchesForRemoval(out, r.output));
         }

         public String describe() {
            return "Removes some recipes from the witches oven.";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            MoreTweaker.forgeRemove(OvenRecipe.class, (r) -> true);
         }

         public String describe() {
            return "Removes all recipes from the witches oven.";
         }
      });
   }
}
