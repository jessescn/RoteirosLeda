package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The selection sort algorithm chooses the smallest element from the array and
 * puts it in the first position. Then chooses the second smallest element and
 * stores it in the second position, and so on until the array is sorted.
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		validation(array, leftIndex, rightIndex);
		for (int i = leftIndex; i <= rightIndex; i++) {
			int menor = i;
			for (int j = i + 1; j <= rightIndex; j++) {
				if (array[menor].compareTo(array[j]) > 0) {
					menor = j;
				}
			}
			Util.swap(array, i, menor);
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
