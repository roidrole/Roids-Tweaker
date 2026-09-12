package moretweaker.hwell;

import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import moretweaker.MoreTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wolforce.Main;
import wolforce.blocks.BlockCore;
import wolforce.items.ItemShard;
import wolforce.recipes.RecipeCoring;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ZenClass("moretweaker.hwell.MoreCoring")
@ZenRegister
@ModOnly("hwell")
public class MoreCoring {
   @ZenMethod
   public static void removeCoring(@Nullable String coreId, @Nullable String shardId) {
      final BlockCore core = coreId == null ? null : Main.cores.get(coreId);
      final ItemShard shard = shardId == null ? null : ItemShard.getFromString(shardId);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            for(Map.Entry<BlockCore, HashMap<ItemShard, RecipeCoring>> entry : RecipeCoring.recipes.entrySet()) {
               if (core == null || core == entry.getKey()) {
                  (entry.getValue()).keySet().removeIf((item) -> shard == null || shard == item);
               }
            }

         }

         public String describe() {
            return "Removes a Hearth Well coring recipe";
         }
      });
   }

   @ZenMethod
   public static void addCoring(final String coreId, final String shardId, IItemStack[] output, IItemStack[] input) {
      final ItemStack[] out = Arrays.stream(output).map(CraftTweakerMC::getItemStack).toArray(ItemStack[]::new);
      final ItemStack[] in = Arrays.stream(input).map(CraftTweakerMC::getItemStack).toArray(ItemStack[]::new);
      MoreTweaker.queuePostInitAction(new IAction() {
         public void apply() {
            RecipeCoring.addCoreRecipe(coreId, shardId, out, in);
         }

         public String describe() {
            return "Adds a Hearth Well coring recipe";
         }
      });
   }
}
