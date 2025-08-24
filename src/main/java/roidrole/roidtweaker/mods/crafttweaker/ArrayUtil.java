package roidrole.roidtweaker.mods.crafttweaker;
//File was originally in CraftTweaker Integration, but I modified more than 50% of it.

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import org.apache.commons.lang3.ArrayUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenRegister
@ZenClass("mods.ctintegration.util.ArrayUtil")
@SuppressWarnings("unused")
public class ArrayUtil {
    @ZenMethod
    public static void sort(int[] array) {
        Arrays.sort(array);
    }
    @ZenMethod
    public static void sort(double[] array) {
        Arrays.sort(array);
    }
    @ZenMethod
    public static void sort(float[] array) {
        Arrays.sort(array);
    }
    @ZenMethod
    public static void sort(String[] array) {
        Arrays.sort(array);
    }
    @ZenMethod
    public static void sort(short[] array) {
        Arrays.sort(array);
    }
    @ZenMethod
    public static void sort(byte[] array) {
        Arrays.sort(array);
    }
    @ZenMethod
    public static void sort(long[] array) {
        Arrays.sort(array);
    }

    @ZenMethod
    public static void reverse(int[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(double[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(float[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(String[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(short[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(byte[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(long[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(char[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }
    @ZenMethod
    public static void reverse(IData[] array) {
        ArrayUtils.reverse(array, 0, array.length);
    }

    @ZenMethod
    public static void fill(int[] array, int element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(double[] array, double element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(float[] array, float element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(String[] array, String element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(short[] array, short element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(byte[] array, byte element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(long[] array, long element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(char[] array, char element) {
        Arrays.fill(array, element);
    }
    @ZenMethod
    public static void fill(IData[] array, IData element) {
        Arrays.fill(array, element);
    }

    @ZenMethod
    public static IData[] merge(IData[] array1, IData[] array2){
        return ArrayUtils.addAll(array1, array2);
    }

    @ZenMethod
    public static IData[] trim(IData[] array, int startIndexInclusive, int endIndexExclusive){
        return ArrayUtils.subarray(array, startIndexInclusive, endIndexExclusive);
    }

    @ZenMethod
    public static IData[] createArray(IData element, int amount){
        IData[] output = new IData[amount];
        Arrays.fill(output, element);
        return output;
    }

    @ZenMethod
    public static String toString(IData[] array){

        StringBuilder output = new StringBuilder();
        output.append('[');
        boolean first = true;
        for (IData value : array) {
            if (first) {
                first = false;
            } else {
                output.append(", ");
            }
            output.append(value.asString());
        }
        output.append(']');

        return output.toString();
    }
}
