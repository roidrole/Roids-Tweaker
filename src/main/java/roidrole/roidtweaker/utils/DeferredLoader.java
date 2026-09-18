package roidrole.roidtweaker.utils;

import java.util.Collection;

public abstract class DeferredLoader {
    private static final Collection<IAction> postInit = new BucketedLinkedCollection<>(8);
    private static final Collection<IAction> loadComplete = new BucketedLinkedCollection<>(0);

    @FunctionalInterface
    public interface IAction {
        void load();
    }

    //Stage-specific functions
    public static void postInit(IAction action){
        postInit.add(action);
    }
    public static void loadComplete(IAction action){
        loadComplete.add(action);
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
}
