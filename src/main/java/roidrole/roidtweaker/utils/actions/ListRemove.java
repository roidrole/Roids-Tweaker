package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;
import java.util.function.Predicate;

public class ListRemove<T> implements DeferredLoader.IAction {

	private final Collection<T> collection;
	private final Predicate<T> condition;

	public ListRemove(Collection<T> collection, Predicate<T> condition){
		this.collection = collection;
		this.condition = condition;
	}

	@Override
	public void load() {
		this.collection.removeIf(condition);
	}
}
