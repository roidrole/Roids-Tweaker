package roidrole.roidtweaker.mods.crafttweaker.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import crafttweaker.api.data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTypeAdapter extends TypeAdapter<IData> {
    public static final TypeAdapter<IData> INSTANCE = new DataTypeAdapter().nullSafe();
    @Override
    public void write(JsonWriter out, IData data) throws IOException {
        if(data == null){
            out.nullValue();
        }
        else if(data instanceof DataMap){
            out.beginObject();
            for (Map.Entry<String, IData> entry : data.asMap().entrySet()) {
                out.name(entry.getKey());
                write(out, entry.getValue());
            }
            out.endObject();
        } else if(data instanceof DataList){
            out.beginArray();
            for (IData element : data.asList()) {
                write(out, element);
            }
            out.endArray();
        } else {
            out.value(data.convert(DataConverterRawString.INSTANCE));
        }
    }

    @Override
    public IData read(JsonReader in) throws IOException {
        switch(in.peek()){
            case NULL: {
                in.nextNull();
                return null;
            }
            case BEGIN_OBJECT:{
                DataMap out = new DataMap(new HashMap<>(), false);
                in.beginObject();
                while (in.hasNext()){
                    out.memberSet(in.nextName(), read(in));
                }
                in.endObject();
                return out;
            }
            case BEGIN_ARRAY:{
                List<IData> out = new ArrayList<>();
                in.beginArray();
                while (in.hasNext()){
                    out.add(read(in));
                }
                in.endArray();
                return new DataList(out, false);
            }
            case BOOLEAN:{
                return new DataBool(in.nextBoolean());
            }
            case NUMBER:{
                return new DataDouble(in.nextDouble());
            }
            case STRING:{
                return new DataString(in.nextString());
            }
        }
        in.close();
        throw new MalformedJsonException("Token "+in.peek()+" is of invalid type");
    }
}
