package roidrole.roidtweaker.utils;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public abstract class DeferredLoader {
    public static final Map<EnumLoadStage, Collection<IAction>> toLoad = new EnumMap<EnumLoadStage, Collection<IAction>>(EnumLoadStage.class){{
        this.put(EnumLoadStage.POST_INIT, new BucketedLinkedCollection<>(8));
        this.put(EnumLoadStage.LOAD_COMPLETE, new BucketedLinkedCollection<>(0));
    }};

    public interface IAction {
        void load();
    }

    //Stage-specific functions
    public static void postInit(IAction action){
        toLoad.get(EnumLoadStage.POST_INIT).add(action);
    }
    public static void loadComplete(IAction action){
        toLoad.get(EnumLoadStage.LOAD_COMPLETE).add(action);
    }

    //Loads the postInit queue
    public static void postInit(){
        toLoad.remove(EnumLoadStage.POST_INIT).forEach(IAction::load);
    }

    public static void loadComplete(){
        toLoad.remove(EnumLoadStage.LOAD_COMPLETE).forEach(IAction::load);
    }
}
