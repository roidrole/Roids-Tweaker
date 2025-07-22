import mods.ctintegration.util.ArrayUtil;
import crafttweaker.item.IItemStack;
import crafttweaker.data.IData;

var myArray as int[] = [0,1,5,3,8];

function printArray(array as int[], comment as string){
    print(comment);
    for value in array{
        print(value as string);
    }
}

//These methods modify the current array
printArray(myArray, "original");
ArrayUtil.sort(myArray);
printArray(myArray, "sorted");
ArrayUtil.reverse(myArray);
printArray(myArray, "reversed");
ArrayUtil.fill(myArray, 1);
printArray(myArray, "filled");

//These methods returns a new array
myArray = ArrayUtil.trim(myArray, 0, 3);
printArray(myArray, "trimmed");
myArray = ArrayUtil.merge(myArray, [2, 5, 7] as int[]);
printArray(myArray, "merged");

printArray(ArrayUtil.createArray(3, 3), "new");