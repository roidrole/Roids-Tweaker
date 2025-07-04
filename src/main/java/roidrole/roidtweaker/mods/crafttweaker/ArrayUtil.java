package roidrole.roidtweaker.mods.crafttweaker;
//File was originally in CraftTweaker Integration, but I modified more than 50% of it.

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import org.apache.commons.lang3.ArrayUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.util.Arrays;
import java.util.Collections;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "util.ArrayUtil")
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
    public static void reverse(IData[] array) {
        ArrayUtils.reverse(array, 0, array.length);
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
        return Collections.nCopies(amount, element).toArray(new IData[amount]);
    }
}
