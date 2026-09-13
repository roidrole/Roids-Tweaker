package roidrole.roidtweaker.utils.predicates;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class TagMatching implements Predicate<ItemStack> {
	ItemStack stack;

	public TagMatching(ItemStack stack){
		this.stack = stack;
	}

	@Override
	public boolean test(ItemStack input) {
		return
			stack.getItem() == input.getItem() &&
			stack.getItemDamage() == input.getItemDamage() &&
			ItemStack.areItemStackShareTagsEqual(stack, input);
	}

	@Override
	public int hashCode() {
		return stack.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TagMatching)){
			return false;
		}
		return stack.equals(((TagMatching) obj).stack);
	}
}
