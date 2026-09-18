package roidrole.roidtweaker.utils.functions;

@FunctionalInterface
public interface BooleanBiFunction<T, U> {
	boolean apply(T t, U u);
}
