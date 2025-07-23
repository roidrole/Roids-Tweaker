package xyz.tcreopargh.ctintegration.data;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.data.IData")
@ZenRegister
@SuppressWarnings("unused")
public class DataExpansion {

    @ZenMethod
    public static String toNBTString(IData data) {
        return DataUtil.toNBTString(data);
    }

    @ZenMethod
    @ZenGetter("rawString")
    public static String getRawString(IData data) {
        return DataUtil.getRawString(data);
    }

    @ZenMethod
    public static String toJson(IData data) {
        return DataUtil.toJson(data);
    }

}
