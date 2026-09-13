package roidrole.roidtweaker.utils.predicates;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class ItemMatching implements Predicate<ItemStack> {
	Item item;

	public ItemMatching(Item item){
		this.item = item;
	}

	@Override
	public boolean test(ItemStack input) {
		return item != input.getItem();
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ItemMatching)){
			return false;
		}
		return item.equals(((ItemMatching) obj).item);
	}
}
