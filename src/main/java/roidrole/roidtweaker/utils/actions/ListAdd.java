package roidrole.roidtweaker.utils.actions;

import moretweaker.MoreTweaker;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Arrays;
import java.util.Collection;

public abstract class ListAdd<T> implements DeferredLoader.IAction {

	public static <T> ListAdd<T> from(Collection<T> collection, T element){
		return new ListAddElement<>(collection, element);
	}
	public static <T> ListAdd<T> from(Collection<T> collection, T[] element){
		return new ListAddCollection<>(collection, Arrays.asList(element));
	}
	public static <T extends IForgeRegistryEntry<T>> ListAdd<T> from(Class<T> registryClass, T element){
		return new RegistryAdd<>(registryClass, element);
	}

	private static class ListAddElement<T> extends ListAdd<T> {
		protected final Collection<T> collection;
		private final T element;

		public ListAddElement(Collection<T> collection, T element) {
			this.collection = collection;
			this.element = element;
		}

		@Override
		public void load() {
			collection.add(element);
		}
	}
	private static class ListAddCollection<T> extends ListAdd<T> {
		protected final Collection<T> collection;
		private final Collection<T> element;

		public ListAddCollection(Collection<T> collection, Collection<T> element) {
			this.collection = collection;
			this.element = element;
		}

		@Override
		public void load() {
			collection.addAll(element);
		}
	}

	private static class RegistryAdd<T extends IForgeRegistryEntry<T>> extends ListAdd<T> {
		protected final Class<T> registryClass;
		private final T element;

		public RegistryAdd(Class<T> registryClass, T element) {
			this.registryClass = registryClass;
			this.element = element;
		}

		@Override
		public void load() {
			MoreTweaker.forgeAdd(registryClass, element);
		}
	}
}
