package roidrole.roidtweaker.utils.functions;

@FunctionalInterface
public interface ObjIntToIntFunction<T> {
	int apply(T obj, int i);
}
