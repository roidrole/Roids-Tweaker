package moretweaker.matteroverdrive;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.data.matter.DamageAwareStackHandler;
import matteroverdrive.data.matter.IMatterEntryHandler;
import matteroverdrive.data.matter.ItemHandler;
import matteroverdrive.data.matter.MatterEntryHandlerAbstract;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roidrole.roidtweaker.utils.Utils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Collection;
import java.util.function.Predicate;

@ZenClass("moretweaker.matteroverdrive.Matter")
@ZenRegister
@ModOnly("matteroverdrive")
public class Matter {
	@ZenMethod
	public static void set(IIngredient input, final int matter) {
		if(input instanceof IOreDictEntry){
			ItemHandler handler = new ItemHandler(matter, true, Integer.MAX_VALUE);
			MatterOverdrive.MATTER_REGISTRY.registerOre(
				((IOreDictEntry) input).getName(),
				handler
			);
			return;
		}
		if(input instanceof IItemStack){
			final Item item = CraftTweakerMC.getItemStack(input).getItem();
			final int damage = ((IItemStack) input).getDamage();
			IMatterEntryHandler<ItemStack> handler;
			if(damage == OreDictionary.WILDCARD_VALUE){
				handler = new ItemHandler(matter, true, Integer.MAX_VALUE);
			} else {
				handler = new DamageAwareStackHandler(damage, matter, true, Integer.MAX_VALUE);
			}
			MatterOverdrive.MATTER_REGISTRY.register(item, handler);
			return;
		}

		final Collection<Item> items = Utils.getItems(input);
		IMatterEntryHandler<ItemStack> handler = new PredicateItemHandler(Utils.getStackMatcher(input), matter);
		for(Item item : items) {
			MatterOverdrive.MATTER_REGISTRY.register(item, handler);
		}
	}

	private static class PredicateItemHandler extends MatterEntryHandlerAbstract<ItemStack> {
		private final Predicate<ItemStack> filter;
		private final int matter;

		private PredicateItemHandler(Predicate<ItemStack> filter, int matter) {
			this.filter = filter;
			this.matter = matter;
			this.priority = Integer.MAX_VALUE;
		}

		@Override
		public int modifyMatter(ItemStack itemStack, int original) {
			if(filter.test(itemStack)){
				return matter;
			}
			return original;
		}

		@Override
		public boolean finalModification(ItemStack itemStack) {
			return filter.test(itemStack);
		}
	}
}
