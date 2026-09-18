package roidrole.roidtweaker.utils.functions;

@FunctionalInterface
public interface ABintDConsumer<A, B, D> {
	void accept(A a, B b, int c, D d);
}
