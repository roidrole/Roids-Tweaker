package roidrole.roidtweaker.mods.crafttweaker.data;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

public class DataConverterRawString implements IDataConverter<String> {
    public static DataConverterRawString INSTANCE = new DataConverterRawString();
    @Override
    public String fromBool(boolean value) {
        return String.valueOf(value);
    }

    @Override
    public String fromByte(byte value) {
        return String.valueOf(value);
    }

    @Override
    public String fromShort(short value) {
        return String.valueOf(value);
    }

    @Override
    public String fromInt(int value) {
        return String.valueOf(value);
    }

    @Override
    public String fromLong(long value) {
        return String.valueOf(value);
    }

    @Override
    public String fromFloat(float value) {
        return String.valueOf(value);
    }

    @Override
    public String fromDouble(double value) {
        return String.valueOf(value);
    }

    @Override
    public String fromString(String value) {
        return value;
    }

    //Starting here, reimplementation of the toString method of the original to preserve compat
    @Override
    public String fromList(List<IData> values) {
        StringBuilder output = new StringBuilder();
        output.append('[');
        boolean first = true;
        for(IData value : values) {
            if(first) {
                first = false;
            } else {
                output.append(", ");
            }
            output.append(value);
        }
        output.append(']');
        return output.toString();
    }

    @Override
    public String fromMap(Map<String, IData> values) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        boolean first = true;
        for(Map.Entry<String, IData> entry : values.entrySet()) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }

            if(isValidIdentifier(entry.getKey())) {
                result.append(entry.getKey());
            } else {
                result.append("\"").append(entry.getKey()).append("\"");
            }

            result.append(": ");
            result.append(entry.getValue());
        }
        result.append('}');
        return result.toString();
    }

    @Override
    public String fromByteArray(byte[] data) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for(byte value : data) {
            if(first)
                first = false;
            else
                result.append(", ");

            result.append(value);
        }
        result.append("] as byte[]");
        return result.toString();
    }

    @Override
    public String fromIntArray(int[] data) {
        StringBuilder result = new StringBuilder();
        result.append('[');
        boolean first = true;
        for(int value : data) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(value);
        }
        result.append("] as int[]");
        return result.toString();
    }

    //Helper
    private boolean isValidIdentifier(String str) {
        if(!Character.isJavaIdentifierStart(str.charAt(0)))
            return false;

        for(int i = 1; i < str.length(); i++) {
            if(!Character.isJavaIdentifierPart(str.charAt(i)))
                return false;
        }

        return true;
    }
}
