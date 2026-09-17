package roidrole.roidtweaker.utils.predicates;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateOr<T> implements Predicate<T> {
	private final Iterable<Predicate<T>> predicates;

	public PredicateOr(Iterable<Predicate<T>> predicates) {
		this.predicates = predicates;
	}

	@Override
	public boolean test(T t) {
		for (Predicate<T> predicate : predicates){
			if(predicate.test(t)){
				return true;
			}
		}
		return false;
	}

	public static Predicate<ItemStack> of(ItemStack[] stacks){
		final List<Predicate<ItemStack>> predicates = new ArrayList<>(stacks.length);
		for (ItemStack stack : stacks) {
			predicates.add(new StackMatching(stack));
		}
		return new PredicateOr<>(predicates);
	}
}
