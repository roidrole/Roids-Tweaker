package moretweaker.bedrockcraft;

import bedrockcraft.ritual.RitualManager;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import moretweaker.Inputs;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.bedrockcraft.BedrockRitual")
@ZenRegister
@ModOnly("bedrockcraft")
public class BedrockRitual {
   @ZenMethod
   public static void addRecipe(IItemStack output, IIngredient center, int bedrockAmount, IIngredient[] inputs) {
      final ItemStack out = InputHelper.toStack(output);
      final Ingredient c = Inputs.getIngredient(Inputs.getObject(center));
      final Ingredient[] in = Inputs.getIngredients(Inputs.getObjects(inputs));
      final int amount = Math.max(0, Math.min(bedrockAmount, 320));
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            RitualManager.addRecipe(out, c, amount, (Object[]) in);
         }

         public String describe() {
            return "Adds a Bedrock Ritual recipe";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            RitualManager.removeRecipes((r) -> Inputs.matchesForRemoval(out, r.output()));
         }

         public String describe() {
            return "Removes some Bedrock Ritual recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            RitualManager.removeRecipes((r) -> true);
         }

         public String describe() {
            return "Removes all recipes for the Bedrock Ritual";
         }
      });
   }
}
