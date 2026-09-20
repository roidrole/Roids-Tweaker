package roidrole.roidtweaker.utils.actions;

import moretweaker.MoreTweaker;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.Utils;
import roidrole.roidtweaker.utils.predicates.PredicateExtract;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ListRemove implements DeferredLoader.IAction {

	public static <T> ListRemove from(Collection<T> collection, Predicate<T> condition){
		return new ListRemoveSimple<>(collection, condition);
	}
	public static <T> ListRemove from(Collection<T> collection, T element){
		return new ListRemoveElement<>(collection, element);
	}
	public static <T> ListRemove fromKey(Map<T, ?> collection, T element){
		return new ListRemoveElement<>(collection.keySet(), element);
	}
	public static <T> ListRemove fromValue(Map<?, T> collection, Predicate<T> element){
		return new ListRemoveSimple<>(collection.values(), element);
	}
	public static <T, U> ListRemove from(Collection<U> collection, Predicate<T> condition, Function<U, T> extractor){
		return new ListRemoveSimple<>(collection, condition, extractor);
	}
	public static <T extends IForgeRegistryEntry<T>> ListRemove from(Class<T> registryClass, Predicate<T> condition){
		return new RegistryRemove<>(registryClass, condition);
	}
	public static <T, U extends IForgeRegistryEntry<U>> ListRemove from(Class<U> registryClass, Predicate<T> condition, Function<U, T> extractor){
		return new RegistryRemove<>(registryClass, condition, extractor);
	}

	private static class ListRemoveSimple<T> extends ListRemove {
		private final Collection<T> collection;
		private final Predicate<T> condition;

		private ListRemoveSimple(Collection<T> collection, Predicate<T> condition){
			this.condition = condition;
			this.collection = collection;
		}
		private <U> ListRemoveSimple(Collection<T> collection, Predicate<U> condition, Function<T, U> extractor){
			this.condition = new PredicateExtract<>(condition, extractor);
			this.collection = collection;
		}

		@Override
		public void load() {
			this.collection.removeIf(condition);
		}
	}
	private static class ListRemoveElement<T> extends ListRemove {
		private final Collection<T> collection;
		private final T element;

		private ListRemoveElement(Collection<T> collection, T element){
			this.element = element;
			this.collection = collection;
		}

		@Override
		public void load() {
			this.collection.remove(element);
		}
	}

	private static class RegistryRemove<T extends IForgeRegistryEntry<T>> extends ListRemove {
		private final IForgeRegistryModifiable<T> registry;
		private final Predicate<T> condition;

		private RegistryRemove(Class<T> registryClass, Predicate<T> condition) {
			this.registry = Utils.getRegistry(registryClass);
			this.condition = condition;
		}
		private <U> RegistryRemove(Class<T> registryClass, Predicate<U> condition, Function<T, U> extractor){
			this.registry = Utils.getRegistry(registryClass);
			this.condition = new PredicateExtract<>(condition, extractor);
		}

		@Override
		public void load() {
			MoreTweaker.forgeRemove(registry, condition);
		}
	}
}
