package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The bubble sort algorithm iterates over the array multiple times, pushing big
 * elements to the right by swapping adjacent elements, until the array is
 * sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		validation(array, leftIndex, rightIndex);
		for (int i = leftIndex; i < rightIndex; i++) {
			bubbleStep(array, rightIndex, leftIndex);
			}
		}

	private void validation(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < 0 || rightIndex < 0 || rightIndex > array.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (rightIndex < leftIndex) {
			throw new IllegalArgumentException();
		}
	}
	
	private void bubbleStep(T[] array, int rightIndex, int leftIndex) {
		for (int i = leftIndex; i < rightIndex; i ++) {
			if (array[i].compareTo(array[i + 1]) > 0) {
				Util.swap(array, i, i + 1);
			}
		}
	}
}
