package sorting.divideAndConquer.threeWayQuicksort;

import java.util.concurrent.ThreadLocalRandom;

import sorting.AbstractSorting;
import util.Util;

public class ThreeWayQuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	/**
	 * No algoritmo de quicksort, selecionamos um elemento como pivot, particionamos
	 * o array colocando os menores a esquerda do pivot e os maiores a direita do
	 * pivot, e depois aplicamos a mesma estrategia recursivamente na particao a
	 * esquerda do pivot e na particao a direita do pivot.
	 * 
	 * Considerando um array com muitoe elementos repetidos, a estrategia do
	 * quicksort pode ser otimizada para lidar de forma mais eficiente com isso.
	 * Essa melhoria eh conhecida como quicksort tree way e consiste da seguinte
	 * ideia: - selecione o pivot e particione o array de forma que * arr[l..i]
	 * contem elementos menores que o pivot * arr[i+1..j-1] contem elementos iguais
	 * ao pivot. * arr[j..r] contem elementos maiores do que o pivot.
	 * 
	 * Obviamente, ao final do particionamento, existe necessidade apenas de ordenar
	 * as particoes contendo elementos menores e maiores do que o pivot. Isso eh
	 * feito recursivamente.
	 **/

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (rightIndex > leftIndex && rightIndex < array.length && leftIndex > -1 && rightIndex > -1) {
			int[] walls = quick(array, leftIndex, rightIndex);
			int first_pivot = walls[0];
			int last_pivot = walls[1];
			sort(array, leftIndex, first_pivot);
			sort(array, last_pivot + 1, rightIndex);

		}
	}

	public int[] quick(T[] array, int leftIndex, int rightIndex) {
		// randominzando o pivot
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
		int left_wall = ordenaIguais(array, leftIndex, wall + 1);
		int[] saida = new int[2];
		saida[0] = left_wall;
		saida[1] = wall + 1;
		return saida;
	}

	public int ordenaIguais(T[] array, int leftIndex, int indexPivot) {
		int k = indexPivot - 1;
		int i = leftIndex;
		while (i < k) {
			if (array[i].compareTo(array[indexPivot]) == 0) {
				Util.swap(array, i, k);
				k--;
			}
			i++;
		}
		return k;

	}

}
