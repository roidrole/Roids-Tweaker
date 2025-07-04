import mods.ctintegration.util.ArrayUtil;
import crafttweaker.item.IItemStack;
import crafttweaker.data.IData;

var myArray as int[] = [0,1,5,3,8];

function printArray(array as int[]){
    print("printing an int array");
    for value in array{
        print(value as string);
    }
}

//These methods modify the current array
printArray(myArray);
ArrayUtil.sort(myArray);
printArray(myArray);
ArrayUtil.reverse(myArray);
printArray(myArray);

//These methods returns a new array
myArray = ArrayUtil.trim(myArray, 0, 3);
printArray(myArray);
myArray = ArrayUtil.merge(myArray, [2, 5, 7]);
printArray(myArray);

printArray(ArrayUtil.createArray(3, 3));