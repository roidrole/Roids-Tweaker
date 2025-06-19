package roidrole.roidtweaker.utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class DeferredLoader {
    public static Map<LoadStageEnum, List<IAction>> toLoad = new EnumMap<>(LoadStageEnum.class);

    public interface IAction {
        void load();
    }

    //Adds in the postInit load queue
    public static void load(IAction action, LoadStageEnum stage){
        if(!toLoad.containsKey(stage)){
            toLoad.put(stage, new ArrayList<>(1));
        }
        toLoad.get(stage).add(action);
    }

    //Loads the postInit queue
    public static void postInit(){
        if(!toLoad.containsKey(LoadStageEnum.POST_INIT)) {
            return;
        }
        toLoad.get(LoadStageEnum.POST_INIT).forEach(IAction::load);
    }
}
