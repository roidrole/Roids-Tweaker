package roidrole.roidtweaker.mods.minecraft.config;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.config.Configuration;
import roidrole.roidtweaker.RoidTweaker;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.io.File;

@ZenRegister
@ZenClass(RoidTweaker.CT_PACKAGE + "config.Reader")
public class Reader {
    @ZenMethod
    public static Config getConfigFile(String path){
        return new Config(new Configuration(new File(path)));
    }
}
