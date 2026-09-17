package roidrole.roidtweaker.utils.predicates;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class StackMatching implements Predicate<ItemStack> {
	Item item;
	int meta;


	public StackMatching(ItemStack stack){
		this.item = stack.getItem();
		this.meta = stack.getItemDamage();
	}
	public StackMatching(Item item, int meta){
		this.item = item;
		this.meta = meta;
	}

	@Override
	public boolean test(ItemStack input) {
		if (item != input.getItem()) {
			return false;
		}
		return meta == input.getMetadata();
	}

	@Override
	public int hashCode() {
		return item.hashCode() ^ meta;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StackMatching)){
			return false;
		}
		return item == ((StackMatching) obj).item && meta == ((StackMatching) obj).meta;
	}
}
