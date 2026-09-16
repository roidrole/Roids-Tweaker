package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ListAdd<T> implements DeferredLoader.IAction {
	private final Collection<T> collection;
	private final Collection<T> elements;

	public ListAdd(Collection<T> collection, T element){
		this.collection = collection;
		this.elements = Collections.singleton(element);
	}

	public ListAdd(Collection<T> collection, T[] element){
		this.collection = collection;
		this.elements = Arrays.asList(element);
	}

	@Override
	public void load() {
		this.collection.addAll(this.elements);
	}
}
