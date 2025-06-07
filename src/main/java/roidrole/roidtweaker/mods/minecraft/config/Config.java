package roidrole.roidtweaker.mods.minecraft.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ZenRegister
@ZenClass(CTIntegration.CTI_PACKAGE + "config.IConfigFile")
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
}
