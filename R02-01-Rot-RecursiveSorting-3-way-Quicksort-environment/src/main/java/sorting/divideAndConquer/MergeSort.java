package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (rightIndex > leftIndex && rightIndex < array.length && leftIndex >  - 1 &&
				rightIndex > - 1) {
			int meio = (rightIndex + leftIndex) / 2;
			sort(array, leftIndex, meio);
			sort(array, meio + 1, rightIndex);
			mergeArray(array, leftIndex, meio, rightIndex);
		}
	}

	private void mergeArray(T[] array, int leftIndex, int meio, int rightIndex) {
		T[] arrayCopia = Arrays.copyOf(array, array.length);
		int i = leftIndex;
		int j = meio + 1;
		int k = leftIndex;
		while (i <= meio && j <= rightIndex) {
			if (arrayCopia[i].compareTo(arrayCopia[j]) < 0) {
				array[k] = arrayCopia[i];
				i++;
			} else {
				array[k] = arrayCopia[j];
				j++;
			}
			k++;
		}

		while (i <= meio) {
			array[k] = arrayCopia[i];
			i++;
			k++;
		}
		while (j <= rightIndex) {
			array[k] = arrayCopia[j];
			j++;
			k++;
		}
	}
	

}
