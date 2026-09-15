package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;

public class ListAdd<T> implements DeferredLoader.IAction {
	private final Collection<T> collection;
	private final T element;

	public ListAdd(Collection<T> collection, T element){
		this.collection = collection;
		this.element = element;
	}

	@Override
	public void load() {
		this.collection.add(this.element);
	}
}
