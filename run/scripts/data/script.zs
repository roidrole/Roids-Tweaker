import crafttweaker.data.IData;
import mods.ctintegration.data.DataUtil;

//This line errors, as expected
//DataUtil.write("../../should_not", {"I AM":"HERE"});

//Implicitly converts to IData
DataUtil.write("scripts/data/write.json", {"I AM":"HERE", "This is null":null});
print(DataUtil.read("scripts/data/read.json5"));
print(DataUtil.getRawString({"My_Mod":"IS AMAZING!", "Cheers to...":"Me!"}));
DataUtil.write("scripts/data/newfolder/data.json", "roidrole was here");