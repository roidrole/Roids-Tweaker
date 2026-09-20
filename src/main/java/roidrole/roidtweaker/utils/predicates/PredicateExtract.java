package roidrole.roidtweaker.utils.predicates;

import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateExtract<T, U> implements Predicate<U> {
	private final Predicate<T> internal;
	private final Function<U, T> extractor;

	public PredicateExtract(Predicate<T> internal, Function<U, T> extractor) {
		this.internal = internal;
		this.extractor = extractor;
	}

	@Override
	public boolean test(U u) {
		return internal.test(extractor.apply(u));
	}
}
