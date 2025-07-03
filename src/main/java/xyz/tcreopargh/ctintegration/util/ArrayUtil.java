package xyz.tcreopargh.ctintegration.util;

import crafttweaker.annotations.ZenRegister;
import org.apache.commons.lang3.ArrayUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.util.Arrays;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "util.ArrayUtil")
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
    public static void sort(char[] array) {
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
}
