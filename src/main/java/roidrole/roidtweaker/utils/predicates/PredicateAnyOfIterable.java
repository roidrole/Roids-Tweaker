package roidrole.roidtweaker.utils.predicates;

import java.util.function.Predicate;

public class PredicateAnyOfIterable<T> implements Predicate<Iterable<T>>{
	private final Predicate<T> internal;

	public PredicateAnyOfIterable(Predicate<T> internal) {
		this.internal = internal;
	}

	@Override
	public boolean test(Iterable<T> ts) {
		for(T t : ts){
			if(internal.test(t)){
				return true;
			}
		}
		return false;
	}

	public static <T> boolean test(Predicate<T> internal, T[] toEvaluate){
		for(T t : toEvaluate){
			if(internal.test(t)){
				return true;
			}
		}
		return false;
	}
}
