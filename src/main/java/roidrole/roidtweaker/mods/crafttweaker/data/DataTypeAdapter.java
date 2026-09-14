package roidrole.roidtweaker.mods.crafttweaker.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import crafttweaker.api.data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataTypeAdapter {
    public static final DataTypeAdapter INSTANCE = new DataTypeAdapter();

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
