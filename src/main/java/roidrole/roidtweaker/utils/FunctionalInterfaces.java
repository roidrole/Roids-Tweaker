package roidrole.roidtweaker.utils;


public class FunctionalInterfaces {
    @FunctionalInterface
    public interface TriFunction<A,B,C,R>{
        R apply(A a, B b, C c);
    }
}
