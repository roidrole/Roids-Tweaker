package roidrole.roidtweaker.utils.actions;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;

import java.util.Arrays;
import java.util.Collection;

public abstract class ListAdd implements DeferredLoader.IAction {

	public static <T> ListAdd from(Collection<T> collection, T element){
		return new ListAddElement<>(collection, element);
	}
	public static <T> ListAdd from(Collection<T> collection, T[] element){
		return new ListAddCollection<>(collection, Arrays.asList(element));
	}
	public static <T extends IForgeRegistryEntry<T>> ListAdd from(Class<T> registryClass, T element){
		return new RegistryAdd<>(registryClass, element);
	}
	public static <T extends IForgeRegistryEntry<T>> ListAdd from(Class<T> registryClass, Collection<T> element){
		return new RegistryAddCollection<>(registryClass, element);
	}

	private static class ListAddElement<T> extends ListAdd {
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
	private static class ListAddCollection<T> extends ListAdd {
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

	private static class RegistryAdd<T extends IForgeRegistryEntry<T>> extends ListAdd {
		protected final Class<T> registryClass;
		private final T element;

		public RegistryAdd(Class<T> registryClass, T element) {
			this.registryClass = registryClass;
			this.element = element;
		}

		@Override
		public void load() {
			IForgeRegistry<T> registry = GameRegistry.findRegistry(registryClass);
			if (element.getRegistryName() == null) {
				element.setRegistryName(Utils.newRL());
			}

			registry.register(element);
		}
	}

	private static class RegistryAddCollection<T extends IForgeRegistryEntry<T>> extends ListAdd {
		protected final Class<T> registryClass;
		private final Collection<T> element;

		public RegistryAddCollection(Class<T> registryClass, Collection<T> element) {
			this.registryClass = registryClass;
			this.element = element;
		}

		@Override
		public void load() {
			IForgeRegistry<T> registry = GameRegistry.findRegistry(registryClass);
			for(T entry: element){
				if (entry.getRegistryName() == null) {
					entry.setRegistryName(Utils.newRL());
				}

				registry.register(entry);
			}
		}
	}
}
