package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;

public class ListClear implements DeferredLoader.IAction{
	private final Collection<?> collection;

	public ListClear(Collection<?> collection) {
		this.collection = collection;
	}

	@Override
	public void load() {
		this.collection.clear();
	}
}
