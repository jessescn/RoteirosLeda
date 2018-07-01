package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		validation(array, leftIndex, rightIndex);
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			T aux = array[i];
			int j = i;

			while (j > 0 && (array[j - 1].compareTo(aux) > 0)) {
				Util.swap(array, j - 1, j);
				j--;
			}

		}
	}

	private void validation(T[] array, int leftIndex, int rightIndex) {
		if (array.length != 0) {
			if (leftIndex < 0 || rightIndex < array.length - 1 || rightIndex < leftIndex - 1) {
				throw new IndexOutOfBoundsException();
			}
		} else {
			if (rightIndex > array.length - 1) {
				throw new IndexOutOfBoundsException();
			}
		}
	}

}
