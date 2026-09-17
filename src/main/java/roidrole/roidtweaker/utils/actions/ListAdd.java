package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Arrays;
import java.util.Collection;

public abstract class ListAdd<T> implements DeferredLoader.IAction {
	protected final Collection<T> collection;


	//private: Use from methods
	private ListAdd(Collection<T> collection){
		this.collection = collection;
	}

	public static <T> ListAdd<T> from(Collection<T> collection, T element){
		return new ListAddElement<>(collection, element);
	}
	public static <T> ListAdd<T> from(Collection<T> collection, T[] element){
		return new ListAddCollection<>(collection, Arrays.asList(element));
	}

	private static class ListAddElement<T> extends ListAdd<T> {
		private final T element;

		public ListAddElement(Collection<T> collection, T element) {
			super(collection);
			this.element = element;
		}

		@Override
		public void load() {
			collection.add(element);
		}
	}
	private static class ListAddCollection<T> extends ListAdd<T> {
		private final Collection<T> element;

		public ListAddCollection(Collection<T> collection, Collection<T> element) {
			super(collection);
			this.element = element;
		}

		@Override
		public void load() {
			collection.addAll(element);
		}
	}
}
