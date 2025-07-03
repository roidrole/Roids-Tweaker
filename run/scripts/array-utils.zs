#loader preinit

import mods.ctintegration.util.ArrayUtil;

val myArray as int[] = [0,1,5,3,8];

function printIntArray(intArray as int[]){
    print("printing an int[]");
    for value in intArray{
        print(value as string);
    }
}

printIntArray(myArray);
ArrayUtil.sort(myArray);
printIntArray(myArray);
ArrayUtil.reverse(myArray);
printIntArray(myArray);