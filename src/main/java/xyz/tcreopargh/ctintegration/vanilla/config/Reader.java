package xyz.tcreopargh.ctintegration.vanilla.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.Tags;

import java.io.File;

@ZenRegister
@ZenClass(Tags.CT_NAMESPACE + "config.Reader")
public class Reader {
    @ZenMethod
    public static Config getConfigFile(String path){
        return new Config(new Configuration(new File(path)));
    }
}
