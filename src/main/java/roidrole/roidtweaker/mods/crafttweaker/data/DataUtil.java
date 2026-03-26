package roidrole.roidtweaker.mods.crafttweaker.data;
//Originally in CT Integration, rewrote JSON handling to use GSON and a DataConverter, added fromNBT

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataBool;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.io.FileUtils;
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
        try {
            JsonReader reader = new JsonReader(new StringReader(jsonString));
            reader.setLenient(true);
            return DataTypeAdapter.INSTANCE.read(reader);
        } catch (IOException e) {
            CraftTweakerAPI.logError("Cannot read jsonString "+jsonString, e);
            return null;
        }
    }

    @ZenMethod
    public static IData fromSNBT(String nbtString) {
        try {
            NBTTagCompound nbt = JsonToNBT.getTagFromJson(nbtString);
            return NBTConverter.from(nbt, false);
        } catch (NBTException e) {
            CraftTweakerAPI.logError("Cannot read nbtString "+nbtString, e);
            return null;
        }
    }

    @ZenMethod
    public static IData parse(String dataString) {
        try {
            JsonReader reader = new JsonReader(new StringReader(dataString));
            reader.setLenient(true);
            return DataTypeAdapter.INSTANCE.read(reader);
        } catch (IOException ignored) { }

        try {
            NBTTagCompound nbt = JsonToNBT.getTagFromJson(dataString);
            return NBTConverter.from(nbt, false);
        } catch (NBTException ignored) { }
        CraftTweakerAPI.logError("Cannot read data string "+dataString);
        return null;
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


    @ZenMethod
    public static IData readSNBT(String file){
        if(file.contains("..")){
            CraftTweakerAPI.logError(".. operation isn't supported in file paths");
            return new DataBool(false);
        }
        FileReader reader;
        try {
            NBTTagCompound nbt = JsonToNBT.getTagFromJson(FileUtils.readFileToString(new File(file), "utf-8"));
            return NBTConverter.from(nbt, false);
        } catch (IOException e) {
            CraftTweakerAPI.logError("An unexpected problem happened when reading file " + file);
            return new DataBool(false);
        } catch (NBTException e) {
            CraftTweakerAPI.logError("Malformed json file " + file);
            return new DataBool(false);
        }
    }

    @ZenMethod
    public static void writeSNBT(String file, IData data){
        if(file.contains("..")){
            throw new UnsupportedOperationException(".. isn't supported in file paths");
        }
        try {
            Files.createDirectories(Paths.get(file).getParent());
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(NBTConverter.from(data).toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("An unexpected problem happened when writing to file " + file);
        }
    }

}
