package roidrole.roidtweaker.utils;


public class FunctionalInterfaces {
    @FunctionalInterface
    public interface VoidBiFunction<T, U>{
        void apply(T t, U u);
    }

    @FunctionalInterface
    public interface BooleanBiFunction<T, U>{
        boolean apply(T t, U u);
    }

    @FunctionalInterface
    public interface FloatTriFunction<A,B,C>{
        float apply(A a, B b, C c);
    }
}
