package roidrole.roidtweaker.mods.forge.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import roidrole.roidtweaker.RoidTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.File;

@ZenRegister
@ZenClass(RoidTweaker.CT_PACKAGE + "config.Reader")
public class Reader {
    @ZenMethod
    public static Config getConfigFile(String path){
        return new Config(new Configuration(new File(path)));
    }
    @ZenMethod
    public static Config getConfig(String path){
        return new Config(new Configuration(new File(path)));
    }
}
