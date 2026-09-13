package roidrole.roidtweaker.utils;


import javax.annotation.Nonnull;
import java.util.*;

public class BucketedLinkedCollection<T> extends AbstractCollection<T> {
	private int size;
	private int arrayIndex;
	private final Node<T> firstNode;
	//Just so add() is O(1)
	private Node<T> lastNode;

	public BucketedLinkedCollection(int capacity){
		//By filling the bitwise representation with 1, we can ensure that the result is 2^n - 1
		//Subtracting 1 just so an initial size of 16 doesn't allocate the 16 array. We know the initial size is imprecise.
		//In the option with a List<Node<T>> for O(1) findNodeAtIndex, initialSize should be ignored.
		//In this mod, findNodeAtIndex should never be called anyway.
		if(capacity <= 1){
			capacity = 1;
		} else {
			capacity -= 1;
			capacity |= capacity >>> 1;
			capacity |= capacity >>> 2;
			capacity |= capacity >>> 4;
			capacity |= capacity >>> 8;
			capacity |= capacity >>> 16;
		}
		this.size = 0;
		this.arrayIndex = 0;
		this.firstNode = new Node<>(capacity);
		this.lastNode = firstNode;
	}

	@Override
	public @Nonnull Iterator<T> iterator() {
		return new Iterator<T>() {
			Node<T> currentNode = firstNode;
			int currentIndex = 0;
			int totalIndex = 0;

			@Override
			public boolean hasNext() {
				System.out.println("hasNext called. currentIndex is "+currentIndex+" total index is " + totalIndex + " size is " + size + " elements are "+ Arrays.toString(currentNode.elements));
				return totalIndex < size;
			}

			@Override
			public T next() {
				if(currentIndex == currentNode.elements.length){
					currentIndex = 0;
					currentNode = currentNode.next;
				}
				totalIndex++;
				return currentNode.elements[currentIndex++];
			}
		};
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(T element) {
		size++;
		if(arrayIndex == lastNode.elements.length){
			lastNode.next = new Node<>(size);
			lastNode = lastNode.next;
			arrayIndex = 0;
		}
		lastNode.elements[arrayIndex++] = element;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		for (T element : collection){
			this.add(element);
		}
		return true;
	}

	@Override
	public void clear() {
		Arrays.fill(this.firstNode.elements, null);
		firstNode.next = null;
		lastNode = firstNode;
		size = 0;
	}

	public static class Node<T> {
		final T[] elements;
		Node<T> next = null;

		@SuppressWarnings("unchecked")
		public Node(int size){
			this.elements = (T[])new Object[size];
		}
	}
}
