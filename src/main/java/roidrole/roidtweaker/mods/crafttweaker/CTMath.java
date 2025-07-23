package roidrole.roidtweaker.mods.crafttweaker;
//Mod was originally in CTUtils, but I modified a significant part of it

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IRandom;
import crafttweaker.mc1120.util.MCRandom;
import net.minecraft.util.math.MathHelper;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Random;


@ZenRegister
@ZenClass("mods.ctutils.utils.Math")
@SuppressWarnings("unused")
public class CTMath {

	@ZenMethod
	public static double max(double d1, double d2) {
		return Math.max(d1, d2);
	}
	@ZenMethod
	public static float max(float f1, float f2) {
		return Math.max(f1, f2);
	}
	@ZenMethod
	public static long max(long l1, long l2) {
		return Math.max(l1, l2);
	}
	@ZenMethod
	public static int max(int i1, int i2) {
		return Math.max(i1, i2);
	}

	@ZenMethod
	public static double min(double d1, double d2) {
		return Math.min(d1, d2);
	}
	@ZenMethod
	public static float min(float f1, float f2) {
		return Math.min(f1, f2);
	}
	@ZenMethod
	public static long min(long l1, long l2) {
		return Math.min(l1, l2);
	}
	@ZenMethod
	public static int min(int i1, int i2) {
		return Math.min(i1, i2);
	}

	@ZenMethod
	public static long floor(double d1) {
		return (long) Math.floor(d1);
	}

	@ZenMethod
	public static long ceil(double d1) {
		return (long) Math.ceil(d1);
	}

	@ZenMethod
	public static double abs(double d1) {
		return Math.abs(d1);
	}
	@ZenMethod
	public static float abs(float f1) {
		return Math.abs(f1);
	}
	@ZenMethod
	public static long abs(long l1) {
		return Math.abs(l1);
	}
	@ZenMethod
	public static int abs(int i1) {
		return Math.abs(i1);
	}

	@ZenMethod
	public static double sin(double d1) {
		return Math.sin(d1);
	}
	@ZenMethod
	public static double cos(double d1) {
		return Math.cos(d1);
	}
	@ZenMethod
	public static double tan(double d1) {
		return Math.tan(d1);
	}

	@ZenMethod
	public static double asin(double d1) {
		return Math.asin(d1);
	}
	@ZenMethod
	public static double acos(double d1) {
		return Math.acos(d1);
	}
	@ZenMethod
	public static double atan(double d1) {
		return Math.atan(d1);
	}

	@ZenMethod
	public static double sinh(double d1) {
		return Math.sinh(d1);
	}
	@ZenMethod
	public static double cosh(double d1) {
		return Math.cosh(d1);
	}
	@ZenMethod
	public static double tanh(double d1) {
		return Math.tanh(d1);
	}

	@ZenMethod
	public static double sqrt(double d1) {
		return Math.sqrt(d1);
	}

	@ZenMethod
	@Deprecated
	public static double random() {
		return Math.random();
	}

	@ZenMethod
	public static int round(float f1) {
		return Math.round(f1);
	}
	@ZenMethod
	public static long round(double d1) {
		return Math.round(d1);
	}

	@ZenMethod
	public static double clamp(double value, double min, double max) {
		return (value < min) ? min : Math.min(value, max);
	}
	@ZenMethod
	public static float clamp(float value, float min, float max) {
		return (value < min) ? min : Math.min(value, max);
	}
	@ZenMethod
	public static int clamp(int value, int min, int max) {
		return (value < min) ? min : Math.min(value, max);
	}
	@ZenMethod
	public static long clamp(long value, long min, long max) {
		return (value < min) ? min : Math.min(value, max);
	}

	@ZenMethod
	public static double log(double input) { return Math.log(input); }
	@ZenMethod
	public static double log10(double input) { return Math.log10(input); }

	//Roidrole additions starting here
	@ZenMethod
	@ZenGetter("e")
	public static double getE(){
		return Math.E;
	}
	@ZenMethod
	@ZenGetter("pi")
	public static double getPi(){
		return Math.PI;
	}
	@ZenMethod
	@ZenGetter("sqrt2")
	public static double getSqrt2(){
		return MathHelper.SQRT_2;
	}

	@ZenMethod
	public static IRandom getRandom(@Optional long seed) {
		if(seed == 0){
			return new MCRandom(new Random());
		} else {
			return new MCRandom(new Random(seed));
		}
	}

	@ZenMethod
	public static double exp(double exponent, @Optional double base){
		if(base == 0){
			return Math.exp(exponent);
		}
		return Math.pow(base, exponent);
	}

	@ZenMethod
	public static double log(double input, @Optional double base){
		if(base == 0 || base == Math.E){
			return Math.log(input);
		} else if (base == 10) {
			return Math.log10(input);
		}
		return Math.log(input)/Math.log(base);
	}

	@ZenMethod
	public static double sign(double value){
		return Math.signum(value);
	}
	@ZenMethod
	public static int sign(int value){
		return Integer.signum(value);
	}
	@ZenMethod
	public static float sign(float value){
		return Math.signum(value);
	}
	@ZenMethod
	public static long sign(long value){
		return Long.signum(value);
	}
	@ZenMethod
	public static double fastInvSqrt(double value){
		return MathHelper.fastInvSqrt(value);
	}
	@ZenMethod
	public static double average(long[] values){
		long output = 0L;
		for(long value : values){
			output += value;
		}
		return (double) output / values.length;
	}
	@ZenMethod
	public static double average(double[] values){
		double output = 0L;
		for(double value : values){
			output += value;
		}
		return output / values.length;
	}
	@ZenMethod
	public static float average(int[] values){
		int output = 0;
		for(int value : values){
			output += value;
		}
		return (float) output / values.length;
	}
	@ZenMethod
	public static float average(float[] values){
		float output = 0L;
		for(float value : values){
			output += value;
		}
		return output / values.length;
	}
	@ZenMethod
	public static double hypot(double cat1, double cat2){
		return Math.hypot(cat1, cat2);
	}
	@ZenMethod
	public static double cbrt(double value){
		return Math.cbrt(value);
	}
}
