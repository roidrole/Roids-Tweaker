package roidrole.roidtweaker.mods.crafttweaker.data;

import com.google.gson.stream.JsonWriter;
import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataConverterJson implements IDataConverter<Void> {
	private final JsonWriter writer;

	public DataConverterJson(JsonWriter writer) {
		this.writer = writer;
	}

	@Override
	public Void fromBool(boolean b) {
		try {
			writer.value(b);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromByte(byte b) {
		try {
			writer.value(b);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromShort(short i) {
		try {
			writer.value(i);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromInt(int i) {
		try {
			writer.value(i);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromLong(long l) {
		try {
			writer.value(l);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromFloat(float v) {
		try {
			writer.value(v);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromDouble(double v) {
		try {
			writer.value(v);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromString(String s) {
		try {
			writer.value(s);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromList(List<IData> list) {
		try {
			writer.beginArray();
			for (IData element : list) {
				if(element == null){
					writer.nullValue();
				} else {
					element.convert(this);
				}
			}
			writer.endArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromMap(Map<String, IData> map) {
		try {
			writer.beginObject();
			for (Map.Entry<String, IData> entry : map.entrySet()) {
				writer.name(entry.getKey());
				IData value = entry.getValue();
				if(value == null){
					writer.nullValue();
				} else {
					value.convert(this);
				}
			}
			writer.endObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromByteArray(byte[] bytes) {
		try {
			writer.beginArray();
			for (byte element : bytes) {
				writer.value(element);
			}
			writer.endArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Void fromIntArray(int[] ints) {
		try {
			writer.beginArray();
			for (int element : ints) {
				writer.value(element);
			}
			writer.endArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}
