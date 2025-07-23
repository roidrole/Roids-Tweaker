package xyz.tcreopargh.ctintegration.data;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringEscapeUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "data.DataUtil")
@SuppressWarnings("unused")
public class DataUtil {

    @ZenMethod
    public static IData fromJson(String jsonString) {
        return parse(jsonString);
    }

    @ZenMethod
    public static IData parse(String jsonString) {
        try {
            NBTTagCompound tagCompound = JsonToNBT.getTagFromJson(jsonString);
            return NBTConverter.from(tagCompound, false);
        } catch (NBTException e) {
            CraftTweakerAPI.logError(e.getMessage(), e);
        }
        return null;
    }

    @ZenMethod
    public static String toNBTString(IData data) {
        return CraftTweakerMC.getNBT(data).toString();
    }

    @ZenMethod
    public static String getRawString(IData data) {
        if (data instanceof DataBool) {
            return data.asBool() ? "true" : "false";
        } else if (data instanceof DataByte) {
            return String.valueOf(data.asByte());
        } else if (data instanceof DataDouble) {
            return String.valueOf(data.asDouble());
        } else if (data instanceof DataFloat) {
            return String.valueOf(data.asFloat());
        } else if (data instanceof DataInt) {
            return String.valueOf(data.asInt());
        } else if (data instanceof DataString) {
            return data.asString();
        } else if (data instanceof DataShort) {
            return String.valueOf(data.asShort());
        } else if (data instanceof DataLong) {
            return String.valueOf(data.asLong());
        } else {
            return data.toString();
        }
    }

    @ZenMethod
    public static String toJson(IData data) {
        if (data instanceof DataBool) {
            return data.asBool() ? "true" : "false";
        } else if (data instanceof DataByte) {
            return String.valueOf(data.asByte());
        } else if (data instanceof DataDouble) {
            return String.valueOf(data.asDouble());
        } else if (data instanceof DataFloat) {
            return String.valueOf(data.asFloat());
        } else if (data instanceof DataInt) {
            return String.valueOf(data.asInt());
        } else if (data instanceof DataString) {
            return "\"" + StringEscapeUtils.escapeJava(data.asString()) + "\"";
        } else if (data instanceof DataShort) {
            return String.valueOf(data.asShort());
        } else if (data instanceof DataLong) {
            return String.valueOf(data.asLong());
        } else if (data instanceof DataByteArray) {
            StringBuilder output = new StringBuilder();
            output.append('[');
            boolean first = true;
            for (byte value : data.asByteArray()) {
                if (first) {
                    first = false;
                } else {
                    output.append(", ");
                }
                output.append(value);
            }
            output.append(']');

            return output.toString();
        } else if (data instanceof DataIntArray) {
            StringBuilder output = new StringBuilder();
            output.append('[');
            boolean first = true;

            for (int value : data.asIntArray()) {
                if (first) {
                    first = false;
                } else {
                    output.append(", ");
                }
                output.append(value);
            }
            output.append(']');
            return output.toString();
        } else if (data instanceof DataList) {
            StringBuilder output = new StringBuilder();
            output.append('[');
            data.asList().forEach(value -> {
                output.append(toJson(value));
                output.append(", ");
            });
            output.delete(output.length() - 2, output.length());
            output.append(']');
            return output.toString();
        } else if (data instanceof DataMap) {
            StringBuilder output = new StringBuilder();
            output.append('{');
            data.asMap().forEach((key, value) -> {
                output.append("\"").append(StringEscapeUtils.escapeJava(key)).append("\"");
                output.append(": ");
                output.append(toJson(value));
                output.append(", ");
            });
            output.delete(output.length() - 2, output.length());
            output.append(']');
            return output.toString();
        } else {
            return "";
        }
    }

}
