package roidrole.roidtweaker.utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class DeferredLoader {
    public static Map<EnumLoadStage, List<IAction>> toLoad = new EnumMap<>(EnumLoadStage.class);

    public interface IAction {
        void load();
    }

    //Adds in the load queue
    public static void load(IAction action, EnumLoadStage stage){
        if(!toLoad.containsKey(stage)){
            toLoad.put(stage, new ArrayList<>(1));
        }
        toLoad.get(stage).add(action);
    }
    public static void load(EnumLoadStage stage, IAction action){
        load(action, stage);
    }

    //Loads the postInit queue
    public static void postInit(){
        if(!toLoad.containsKey(EnumLoadStage.POST_INIT)) {
            return;
        }
        toLoad.get(EnumLoadStage.POST_INIT).forEach(IAction::load);
    }

    public static void loadComplete(){
        if(!toLoad.containsKey(EnumLoadStage.LOAD_COMPLETE)) {
            return;
        }
        toLoad.get(EnumLoadStage.LOAD_COMPLETE).forEach(IAction::load);
    }
}
