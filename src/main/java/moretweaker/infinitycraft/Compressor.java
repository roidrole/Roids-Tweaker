package moretweaker.infinitycraft;

import com.rowan662.infinitycraft.machines.CompressorRecipes;
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
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.infinitycraft.Compressor")
@ZenRegister
@ModOnly("infinitycraft")
@SuppressWarnings("unused")
public class Compressor {
   @ZenMethod
   public static void addRecipe(IIngredient input, IItemStack output, @Optional final int experience) {
      final CraftingPart in = Inputs.getPart(input);
      final ItemStack out = CraftTweakerMC.getItemStack(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            for(ItemStack stack : in.toStackList()) {
               CompressorRecipes.instance().addCompressingRecipe(stack, out, (float)experience);
            }

         }

         public String describe() {
            return "Adds some infinitycraft compressor recipes";
         }
      });
   }

   @ZenMethod
   public static void removeRecipe(IIngredient output) {
      final Object out = Inputs.getObject(output);
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            CompressorRecipes.instance().getCompressingList().values().removeIf((s) -> Inputs.matchesForRemoval(out, s));
         }

         public String describe() {
            return "Removes some infinitycraft compressor recipes";
         }
      });
   }

   @ZenMethod
   public static void removeAll() {
      CraftTweakerAPI.apply(new IAction() {
         public void apply() {
            CompressorRecipes.instance().getCompressingList().clear();
         }

         public String describe() {
            return "Removes all recipes for the infinitycraft Compressor";
         }
      });
   }
}
