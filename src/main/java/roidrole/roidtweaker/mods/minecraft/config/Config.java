package roidrole.roidtweaker.mods.minecraft.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import roidrole.roidtweaker.RoidTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass(RoidTweaker.CT_PACKAGE + "config.IConfigFile")
public class Config {
    Configuration config;

    public Config(Configuration config){
        this.config = config;
    }

    @ZenMethod
    public boolean getBool(String category, String name) {
        return this.config.getCategory(category).get(name).getBoolean();
    }

    @ZenMethod
    public boolean getBoolean(String category, String name) {
        return this.config.getCategory(category).get(name).getBoolean();
    }

    @ZenMethod
    public String getString(String category, String name) {
        return this.config.getCategory(category).get(name).getString();
    }

    @ZenMethod
    public int getInt(String category, String name) {
        return this.config.getCategory(category).get(name).getInt();
    }

    @ZenMethod
    public double getDouble(String category, String name) {
        return this.config.getCategory(category).get(name).getDouble();
    }

    @ZenMethod
    public long getLong(String category, String name) {
        return this.config.getCategory(category).get(name).getLong();
    }

    @ZenMethod
    public boolean[] getBooleanArray(String category, String name) {
        return this.config.getCategory(category).get(name).getBooleanList();
    }

    @ZenMethod
    public String[] getStringArray(String category, String name) {
        return this.config.getCategory(category).get(name).getStringList();
    }

    @ZenMethod
    public int[] getIntArray(String category, String name) {
        return this.config.getCategory(category).get(name).getIntList();
    }

    @ZenMethod
    public boolean getBool(String category, String name, boolean defaultvalue) {
        return this.config.getCategory(category).get(name).getBoolean(defaultvalue);
    }

    @ZenMethod
    public boolean getBoolean(String category, String name, boolean defaultvalue) {
        return this.config.getCategory(category).get(name).getBoolean(defaultvalue);
    }

    @ZenMethod
    public String getString(String category, String name, String defaultvalue) {
        return this.config.get(category, name, defaultvalue).getString();
    }

    @ZenMethod
    public int getInt(String category, String name, int defaultvalue) {
        return this.config.getCategory(category).get(name).getInt(defaultvalue);
    }

    @ZenMethod
    public double getDouble(String category, String name, double defaultvalue) {
        return this.config.getCategory(category).get(name).getDouble(defaultvalue);
    }

    @ZenMethod
    public long getLong(String category, String name, long defaultvalue) {
        return this.config.getCategory(category).get(name).getLong(defaultvalue);
    }

    @ZenMethod
    public boolean[] getBooleanArray(String category, String name, boolean[] defaultvalue) {
        return this.config.get(category, name, defaultvalue).getBooleanList();
    }

    @ZenMethod
    public String[] getStringArray(String category, String name, String[] defaultvalue) {
        return this.config.get(category, name, defaultvalue).getStringList();
    }

    @ZenMethod
    public int[] getIntArray(String category, String name, int[] defaultvalue) {
        return this.config.get(category, name, defaultvalue).getIntList();
    }

    @ZenMethod
    public boolean getBool(String category, String name, boolean defaultvalue, String comment) {
        return this.config.get(category, name, comment).getBoolean(defaultvalue);
    }

    @ZenMethod
    public boolean getBoolean(String category, String name, boolean defaultvalue, String comment) {
        return this.config.get(category, name, comment).getBoolean(defaultvalue);
    }

    @ZenMethod
    public String getString(String category, String name, String defaultvalue, String comment) {
        return this.config.get(category, name, defaultvalue, comment).getString();
    }

    @ZenMethod
    public int getInt(String category, String name, int defaultvalue, String comment) {
        return this.config.get(category, name, comment).getInt(defaultvalue);
    }

    @ZenMethod
    public double getDouble(String category, String name, double defaultvalue, String comment) {
        return this.config.get(category, name, comment).getDouble(defaultvalue);
    }

    @ZenMethod
    public long getLong(String category, String name, long defaultvalue, String comment) {
        return this.config.get(category, name, comment).getLong(defaultvalue);
    }

    @ZenMethod
    public boolean[] getBooleanArray(String category, String name, boolean[] defaultvalue, String comment) {
        return this.config.get(category, name, defaultvalue, comment).getBooleanList();
    }

    @ZenMethod
    public String[] getStringArray(String category, String name, String[] defaultvalue, String comment) {
        return this.config.get(category, name, defaultvalue, comment).getStringList();
    }

    @ZenMethod
    public int[] getIntArray(String category, String name, int[] defaultvalue, String comment) {
        return this.config.get(category, name, defaultvalue, comment).getIntList();
    }
}
