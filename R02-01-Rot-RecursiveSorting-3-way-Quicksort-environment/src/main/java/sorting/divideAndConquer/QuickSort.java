package sorting.divideAndConquer;

import java.util.concurrent.ThreadLocalRandom;

import sorting.AbstractSorting;
import util.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

   @Override
   public void sort(T[] array, int leftIndex, int rightIndex) {
      if (rightIndex > leftIndex && rightIndex < array.length && leftIndex > -1 && rightIndex > -1) {
         int index_pivot = quick(array, leftIndex, rightIndex);
         sort(array, leftIndex, index_pivot - 1);
         sort(array, index_pivot + 1, rightIndex);

      }
   }

   public int quick(T[] array, int leftIndex, int rightIndex) {
	  //randomizando o pivot
	  int index = ThreadLocalRandom.current().nextInt(leftIndex, rightIndex + 1);

	  Util.swap(array, index, rightIndex);

      T pivot = array[rightIndex];
      
      int wall = leftIndex - 1;
      for (int i = leftIndex; i < rightIndex; i++) {
         if (array[i].compareTo(pivot) <= 0) {
            wall++;
            Util.swap(array, wall, i);
         }
      }
      
      Util.swap(array, wall + 1, rightIndex);
      
      return wall + 1;
      
   }

}
