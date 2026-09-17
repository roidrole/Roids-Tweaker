package roidrole.roidtweaker.utils.predicates;

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
}
