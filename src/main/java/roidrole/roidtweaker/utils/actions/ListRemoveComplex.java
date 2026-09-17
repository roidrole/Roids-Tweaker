package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListRemoveComplex<T, U> implements DeferredLoader.IAction {
	private final Collection<U> collection;
	private final Predicate<T> condition;
	private final Function<U, T> extractor;

	public ListRemoveComplex(Collection<U> collection, Predicate<T> condition, Function<U, T> extractor){
		this.collection = collection;
		this.condition = condition;
		this.extractor = extractor;
	}

	@Override
	public void load() {
		this.collection.removeIf(element -> condition.test(extractor.apply(element)));
	}
}
