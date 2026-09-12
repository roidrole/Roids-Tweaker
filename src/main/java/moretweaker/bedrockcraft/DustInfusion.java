package moretweaker.bedrockcraft;

import bedrockcraft.dustInfusion.BedrockDustRecipes;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import moretweaker.MoreTweaker;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("moretweaker.bedrockcraft.DustInfusion")
@ZenRegister
@ModOnly("bedrockcraft")
public class DustInfusion {
   @ZenMethod
   public static void addRecipe(IItemStack block, IItemStack output) {
      Item blockItem = InputHelper.toStack(block).getItem();
      final ItemStack out = InputHelper.toStack(output);
      final Block b = Block.getBlockFromItem(blockItem);
      if (b != null && b != Blocks.AIR) {
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               BedrockDustRecipes.addRecipe(b, out);
            }

            public String describe() {
               return "Adds a dust infusion recipe";
            }
         });
      } else {
         CraftTweakerAPI.logError("For bedrockcraft DustInfusion: block is required but item given");
      }
   }

   @ZenMethod
   public static void removeRecipe(IItemStack block) {
      final Block b = Block.getBlockFromItem(InputHelper.toStack(block).getItem());
      if (b != null && b != Blocks.AIR) {
         MoreTweaker.queuePostInitAction(new IAction() {
            public void apply() {
               BedrockDustRecipes.removeRecipe(b);
            }

            public String describe() {
               return "Removes a dust infusion recipe";
            }
         });
      } else {
         CraftTweakerAPI.logError("For bedrockcraft DustInfusion: block is required but item given");
      }
   }
}
