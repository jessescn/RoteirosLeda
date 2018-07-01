package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array != null && array.length > 1 && rightIndex < array.length && rightIndex > leftIndex && rightIndex >= 0
				&& leftIndex >= 0) {

			int i = leftIndex;
			while (i <= rightIndex) {
				if (i == leftIndex) {
					i++;
				} else if (array[i].compareTo(array[i - 1]) >= 0) {
					i++;
				} else {
					Util.swap(array, i, i - 1);
					i--;
				}
			}

		}
	}
}
