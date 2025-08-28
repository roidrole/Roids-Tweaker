package roidrole.roidtweaker.mods.crafttweaker.data;
//Originally in CT Integration, rewrote JSON handling to use GSON and a DataConverter

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataBool;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@ZenRegister
@ZenClass("mods.ctintegration.data.DataUtil")
@SuppressWarnings("unused")
public class DataUtil {

    @ZenMethod
    public static IData fromJson(String jsonString) {
        return parse(jsonString);
    }

    @ZenMethod
    public static IData parse(String jsonString) {
        try {
            return DataTypeAdapter.INSTANCE.read(new JsonReader(new StringReader(jsonString)));
        } catch (IOException e) {
            CraftTweakerAPI.logError("Cannot read jsonString "+jsonString, e);
            return null;
        }
    }

    @ZenMethod
    public static String toNBTString(IData data) {
        return CraftTweakerMC.getNBT(data).toString();
    }

    @ZenMethod
    public static String getRawString(IData data) {
        return data.convert(DataConverterRawString.INSTANCE);
    }

    @ZenMethod
    public static String toJson(IData data) {
        return DataTypeAdapter.INSTANCE.toJson(data);
    }

    @ZenMethod
    public static IData read(String file){
        if(file.contains("..")){
            CraftTweakerAPI.logError(".. operation isn't supported in file paths");
            return new DataBool(false);
        }
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e){
            CraftTweakerAPI.logError("Trying to read non-existent file: "+file);
            return new DataBool(false);
        }
        reader.setLenient(true);
        IData out;
        try {
            out = DataTypeAdapter.INSTANCE.read(reader);
            reader.close();
        } catch (IOException e) {
            CraftTweakerAPI.logError("An unexpected problem happened when reading file " + file);
            return new DataBool(false);
        }
        return out;
    }

    @ZenMethod
    public static void write(String file, IData data){
        if(file.contains("..")){
            throw new UnsupportedOperationException(".. isn't supported in file paths");
        }
        try {
            Files.createDirectories(Paths.get(file).getParent());
            JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter(file)));
            writer.setLenient(true);
            DataTypeAdapter.INSTANCE.write(writer, data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("An unexpected problem happened when writing to file " + file);
        }
    }

}
