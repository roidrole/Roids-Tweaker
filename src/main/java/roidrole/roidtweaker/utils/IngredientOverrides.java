package roidrole.roidtweaker.utils;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class IngredientOverrides {
	private Predicate<ItemStack>[] keys;
	private boolean[] values;
	private int size;

	@SuppressWarnings("unchecked")
	public IngredientOverrides(int expectedSize){
		this.size = 0;
		this.keys = new Predicate[expectedSize];
		this.values = new boolean[expectedSize];
	}

	@SuppressWarnings("unchecked")
	public IngredientOverrides(){
		this.size = 0;
		this.keys = new Predicate[16];
		this.values = new boolean[16];
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void put(Predicate<ItemStack> key, boolean value) {
		if(size == keys.length){
			resize();
		}
		keys[size] = key;
		values[size] = value;
		size++;
	}

	public void remove(Predicate<ItemStack> key) {
		for (int i = 0; i < size; i++) {
			if(key.equals(keys[i])){
				removeByIndex(i);
			}
		}
	}

	public Boolean getOverride(ItemStack stack){
		for (int i = 0; i < size; i++) {
			if(keys[i].test(stack)){
				return values[i];
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void resize(){
		size *= 2;

		Predicate<ItemStack>[] oldKeys = keys;
		keys = new Predicate[size];
		System.arraycopy(oldKeys, 0, keys, 0, oldKeys.length);

		boolean[] oldValues = values;
		values = new boolean[size];
		System.arraycopy(oldValues, 0, values, 0, oldValues.length);
	}

	private void removeByIndex(int i){
		size--;
		System.arraycopy(keys, i + 1, keys, i, keys.length - i);
		System.arraycopy(values, i + 1, values, i, keys.length - i);
	}
}
