package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array != null && array.length > 1 && rightIndex < array.length && rightIndex > leftIndex && rightIndex >= 0
				&& leftIndex >= 0) {
			int gap = (int) ((rightIndex - leftIndex) / 1.3);
			int i = 0;

			while (gap > 0 && i < rightIndex) {
				i = leftIndex;

				while ((i + gap) <= rightIndex) {
					if (array[i].compareTo(array[i + gap]) > 0) {

						Util.swap(array, i, i + gap);
					}
					i++;
				}
				gap = (int) (gap / 1.3);
			}
		}
	}
}
