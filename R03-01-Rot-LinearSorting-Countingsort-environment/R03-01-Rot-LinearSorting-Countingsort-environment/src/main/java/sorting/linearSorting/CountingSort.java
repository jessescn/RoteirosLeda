package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		counting(array, leftIndex, rightIndex);
	}

	public void counting(Integer[] array, int leftIndex, int rightIndex) {
		if(leftIndex <= rightIndex + 1 && rightIndex < array.length && 
				leftIndex >= 0 && rightIndex >= - 1) {
		int[] valores = encontraMenorMaior(array, leftIndex, rightIndex);
		int menor = valores[0];
		int maior = valores[1];
		int[] qtd = new int[(maior - menor) + 1];

		Arrays.fill(qtd, 0);

		for (int k = leftIndex; k <= rightIndex; k++) {
			int valor = array[k];
			qtd[valor - menor]++;
		}

		// Soma recursiva
		for (int l = 1; l < qtd.length; l++) {
			qtd[l] += qtd[l - 1];
		}

		Integer[] novoArray = new Integer[array.length];

		for (int m = rightIndex; m >= leftIndex ; m--) {
			qtd[array[m] - menor]--;
			int index = qtd[array[m] - menor];
			novoArray[index] = array[m];

		}
		completaArray(array, novoArray, leftIndex, rightIndex);

		}
	}

	private void completaArray(Integer[] array1, Integer[] array2, int leftIndex, int rightIndex) {
		for (int i = leftIndex; i <= rightIndex; i++) {
			array1[i] = array2[i -  leftIndex];
		}
	}

	private int[] encontraMenorMaior(Integer[] array, int leftIndex, int rightIndex) {
		int menor = 0;
		int maior = 0;
		int[] saida = new int[2];
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			if (array[i] < menor) {
				menor = array[i];
			}
			if (array[i] > maior) {
				maior = array[i];
			}
		}
		saida[0] = menor;
		saida[1] = maior;
		return saida;
	}
}
