package roidrole.roidtweaker.mods.forge.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.File;

@ZenRegister
@ZenClass("mods.roidtweaker.forge.config.Reader")
public class Reader {
    @ZenMethod
    public static IConfigFile getConfigFile(String path){
        return new IConfigFile(new Configuration(new File(path)));
    }
    @ZenMethod
    public static IConfigFile getConfig(String path){
        return new IConfigFile(new Configuration(new File(path)));
    }
}
