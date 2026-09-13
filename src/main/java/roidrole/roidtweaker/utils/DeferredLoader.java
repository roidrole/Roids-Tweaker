package roidrole.roidtweaker.utils;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public abstract class DeferredLoader {
    public static Map<EnumLoadStage, Collection<IAction>> toLoad = new EnumMap<EnumLoadStage, Collection<IAction>>(EnumLoadStage.class){{
        this.put(EnumLoadStage.POST_INIT, new BucketedLinkedCollection<>(8));
        this.put(EnumLoadStage.LOAD_COMPLETE, new BucketedLinkedCollection<>(0));
    }};

    public interface IAction {
        void load();
    }

    //Adds in the load queue
    public static void load(IAction action, EnumLoadStage stage){
        toLoad.get(stage).add(action);
    }
    public static void load(EnumLoadStage stage, IAction action){
        load(action, stage);
    }

    //Loads the postInit queue
    public static void postInit(){
        toLoad.remove(EnumLoadStage.POST_INIT).forEach(IAction::load);
    }

    public static void loadComplete(){
        toLoad.remove(EnumLoadStage.LOAD_COMPLETE).forEach(IAction::load);
    }
}
