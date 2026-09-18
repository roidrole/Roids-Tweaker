package roidrole.roidtweaker.utils.functions;

@FunctionalInterface
public interface FloatTriFunction<A, B, C> {
	float apply(A a, B b, C c);
}
