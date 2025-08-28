package xyz.tcreopargh.ctintegration.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@ZenRegister
@ZenClass("mods.ctintegration.util.RawLogger")
@SuppressWarnings("unused")
public class RawLogger {

    private static PrintWriter printWriter;

    @ZenMethod
    public static void logRaw(String message) {
		if(printWriter == null){
			try {
				printWriter = new PrintWriter("crafttweaker_raw.log");
			} catch (FileNotFoundException e) {
				CraftTweakerAPI.logError("Couldn't create logging file", e);
				return;
			}
		}
        printWriter.write(message);
        printWriter.write("\n");
        printWriter.flush();
    }
}
