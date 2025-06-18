package roidrole.roidtweaker.mods.forge.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.roidtweaker.forge.config.IConfigFile")
public class IConfigFile {
    Configuration config;

    public IConfigFile(Configuration config){
        this.config = config;
    }

    @ZenMethod
    public boolean getBool(String category, String name, @Optional Boolean defaultValue, @Optional String comment) {
        if(defaultValue == null){
            return this.config.getCategory(category).get(name).getBoolean();
        }
        return this.config.get(category, name, defaultValue, comment).getBoolean();
    }

    @ZenMethod
    public boolean getBoolean(String category, String name, @Optional Boolean defaultValue, @Optional String comment) {
        if(defaultValue == null){
            return this.config.getCategory(category).get(name).getBoolean();
        }
        return this.config.get(category, name, defaultValue, comment).getBoolean();
    }

    @ZenMethod
    public String getString(String category, String name, @Optional String defaultValue, @Optional String comment) {
        if(defaultValue == null){
            return this.config.getCategory(category).get(name).getString();
        }
        return this.config.get(category, name, defaultValue, comment).getString();
    }

    @ZenMethod
    public int getInt(String category, String name, @Optional Integer defaultValue, @Optional String comment, @Optional Integer min, @Optional Integer max) {
        if(defaultValue == null){
            return this.config.getCategory(category).get(name).getInt();
        }
        if(min != null && max != null){
            this.config.get(category, name, defaultValue, comment, min, max).getInt();
        }
        return this.config.get(category, name, defaultValue, comment).getInt();
    }

    @ZenMethod
    public double getDouble(String category, String name, @Optional Double defaultValue, @Optional String comment, @Optional Double min, @Optional Double max) {
        if(defaultValue == null){
            return this.config.getCategory(category).get(name).getDouble();
        }
        if(min != null && max != null){
            this.config.get(category, name, defaultValue, comment, min, max).getDouble();
        }
        return this.config.get(category, name, defaultValue, comment).getDouble();
    }

    @ZenMethod
    public long getLong(String category, String name, @Optional Long defaultValue, @Optional String comment, @Optional Long min, @Optional Long max) {
        if(defaultValue == null){
            return this.config.getCategory(category).get(name).getLong();
        }
        if(min != null && max != null){
            this.config.get(category, name, defaultValue, comment, min, max).getLong();
        }
        return this.config.get(category, name, defaultValue, comment).getLong();
    }

    @ZenMethod
    public boolean[] getBooleanArray(String category, String name, @Optional boolean[] defaultValue, @Optional String comment, @Optional boolean isLengthFixed, @Optional("1") int maxLength) {
        if(defaultValue.length == 0){
            return this.config.getCategory(category).get(name).getBooleanList();
        }
        return this.config.get(category, name, defaultValue, comment, isLengthFixed, maxLength).getBooleanList();
    }

    @ZenMethod
    public String[] getStringArray(String category, String name, @Optional String[] defaultValue, @Optional String comment, @Optional boolean isLengthFixed, @Optional("1") int maxLength) {
        if(defaultValue.length == 0){
            return this.config.getCategory(category).get(name).getStringList();
        }
        return this.config.get(category, name, defaultValue, comment, isLengthFixed, maxLength, null).getStringList();
    }

    @ZenMethod
    public int[] getIntArray(String category, String name, @Optional int[] defaultValue, @Optional String comment, @Optional Integer min, @Optional Integer max, @Optional boolean isLengthFixed, @Optional("1") int maxLength) {
        if(defaultValue.length == 0){
            return this.config.getCategory(category).get(name).getIntList();
        }
        if(min != null && max != null){
            this.config.get(category, name, defaultValue, comment, min, max, isLengthFixed, maxLength).getIntList();
        }
        return this.config.get(category, name, defaultValue, comment).getIntList();
    }
}
