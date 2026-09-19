package roidrole.roidtweaker.utils.actions;

import moretweaker.MoreTweaker;
import net.minecraftforge.registries.IForgeRegistryEntry;
import roidrole.roidtweaker.utils.DeferredLoader;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ListRemove<T> implements DeferredLoader.IAction {
	protected final Predicate<T> condition;

	protected ListRemove(Predicate<T> condition) {
		this.condition = condition;
	}

	public static <T> ListRemove<T> from(Collection<T> collection, Predicate<T> condition){
		return new ListRemoveSimple<>(collection, condition);
	}
	public static <T> ListRemove<T> fromKey(Map<T, ?> collection, T element){
		return new ListRemoveSimple<>(collection.keySet(), Predicate.isEqual(element));
	}
	public static <T> ListRemove<T> fromValue(Map<?, T> collection, Predicate<T> element){
		return new ListRemoveSimple<>(collection.values(), element);
	}
	public static <T, U> ListRemove<T> from(Collection<U> collection, Predicate<T> condition, Function<U, T> extractor){
		return new ListRemoveExtract<>(collection, condition, extractor);
	}
	public static <T extends IForgeRegistryEntry<T>> ListRemove<T> from(Class<T> registryClass, Predicate<T> condition){
		return new RegistryRemove<>(registryClass, condition);
	}

	private static class ListRemoveSimple<T> extends ListRemove<T> {
		private final Collection<T> collection;

		public ListRemoveSimple(Collection<T> collection, Predicate<T> condition){
			super(condition);
			this.collection = collection;
		}

		@Override
		public void load() {
			this.collection.removeIf(condition);
		}
	}

	private static class ListRemoveExtract<T, U> extends ListRemove<T> {

		private final Collection<U> collection;
		private final Function<U, T> extractor;

		public ListRemoveExtract(Collection<U> collection, Predicate<T> condition, Function<U, T> extractor){
			super(condition);
			this.collection = collection;
			this.extractor = extractor;
		}

		@Override
		public void load() {
			this.collection.removeIf(element -> condition.test(extractor.apply(element)));
		}
	}

	private static class RegistryRemove<T extends IForgeRegistryEntry<T>> extends ListRemove<T> {
		private final Class<T> registryClass;

		private RegistryRemove(Class<T> registryClass, Predicate<T> condition) {
			super(condition);
			this.registryClass = registryClass;
		}

		@Override
		public void load() {
			MoreTweaker.forgeRemove(registryClass, condition);
		}
	}
}
