package roidrole.roidtweaker.utils;

import mezz.jei.api.IJeiRuntime;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class DeferredLoader {
    private static final Collection<IAction> postInit = new BucketedLinkedCollection<>(8);
    private static final Collection<IAction> loadComplete = new BucketedLinkedCollection<>(0);
    private static final Collection<IActionConsume<IJeiRuntime>> jeiRuntime = new BucketedLinkedCollection<>(0);

    @FunctionalInterface
    public interface IAction {
        void load();
    }
    @FunctionalInterface
    public interface IActionConsume<T> extends Consumer<T> {
        void load(T input);

        default void accept(T t){
            load(t);
        }
    }

    //Stage-specific functions
    public static void postInit(IAction action){
        postInit.add(action);
    }
    public static void loadComplete(IAction action){
        loadComplete.add(action);
    }
    public static void jeiRuntime(IActionConsume<IJeiRuntime> action){
        jeiRuntime.add(action);
    }

    //Loads the postInit queue
    public static void postInit(){
        postInit.forEach(IAction::load);
        postInit.clear();
    }

    public static void loadComplete(){
        loadComplete.forEach(IAction::load);
        loadComplete.clear();
    }

    public static void jeiRuntime(IJeiRuntime runtime){
        jeiRuntime.forEach(action -> action.accept(runtime));
        jeiRuntime.clear();
    }
}
