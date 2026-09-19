package moretweaker.hwell;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import roidrole.roidtweaker.utils.DeferredLoader;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wolforce.Main;
import wolforce.blocks.BlockCore;
import wolforce.items.ItemShard;
import wolforce.recipes.RecipeCoring;

import javax.annotation.Nullable;

@ZenClass("moretweaker.hwell.MoreCoring")
@ZenRegister
@ModOnly("hwell")
@SuppressWarnings("unused")
public class MoreCoring {
	@ZenMethod
	public static void removeCoring(@Nullable String coreId, @Nullable String shardId) {
		final BlockCore core = coreId == null ? null : Main.cores.get(coreId);
		final ItemShard shard = shardId == null ? null : ItemShard.getFromString(shardId);
		DeferredLoader.postInit(() -> RecipeCoring.recipes.forEach((key, value) -> {
			if (core == null || core == key) {
				value.keySet().removeIf((item) -> shard == null || shard == item);
			}
		}));
	}
}
