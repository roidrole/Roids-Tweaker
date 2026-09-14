import crafttweaker.data.IData;
import mods.ctintegration.data.DataUtil;

//This line errors, as expected
//DataUtil.write("../../should_not", {"I AM":"HERE"});

//Implicitly converts to IData
DataUtil.write("scripts/data/write.json", {
	"I AM":"HERE",
	"This is null":null,
	"number_int":3 as int,
	"number_double":3 as double,
	map:{
		intArray:[1, 2, 3] as int[],
		byteArray:[10, 20, 30] as byte[],
		dataList:[
			{key: "value"},
			"word",
			6.4
		] as IData
	}
});
DataUtil.write("scripts/data/null.json", null);
DataUtil.write("scripts/data/newfolder/data.json", "roidrole was here");

print(DataUtil.read("scripts/data/read.json5"));

// getRawString and toJson are usually equivalent, but raw string has more information (as byte[], notably)
var data as IData = {"key1":"val1", "byteArray":[1, 2, 3] as byte[], "intArray":[1, 2, 3] as int[]};
print("Raw string: ");
print(DataUtil.getRawString(data));
print("Json string: ");
print(DataUtil.toJson(data));

data = DataUtil.fromJson('{Aspects: [{amount: 10, key: "aer"}]}');
print(data);
data = DataUtil.readSNBT("scripts/data/tcon_pickaxe.snbt");
print(data.tag.StatsOriginal);