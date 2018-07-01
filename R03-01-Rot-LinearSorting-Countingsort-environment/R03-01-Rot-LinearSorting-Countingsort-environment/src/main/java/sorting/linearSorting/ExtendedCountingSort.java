package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if(leftIndex <= rightIndex + 1 && rightIndex < array.length && 
				leftIndex >= 0 && rightIndex >= - 1) {
			
		int[] valores = encontraMenorMaior(array, leftIndex, rightIndex);
		int menor = valores[0];
		int maior = valores[1];
		int[] qtd = new int[(maior - menor) + 1];
		
		// completando o array com zeros
		Arrays.fill(qtd, 0);
		
		// contando a qtd de cada valor dentro do array e incrementando no respectivo index
		// do array qtd
		for (int k = leftIndex; k <= rightIndex; k++) {
			int valor = array[k];
			qtd[valor - menor]++;
		}

		// Soma recursiva
		for (int l = 1; l < qtd.length; l++) {
			qtd[l] += qtd[l - 1];
		}
		
		// copia do array que possuira o resultado ordenado
		Integer[] novoArray = new Integer[array.length];

		for (int m = rightIndex; m >= leftIndex; m--) {
			qtd[array[m] - menor]--;
			int index = qtd[array[m] - menor];
			novoArray[index] = array[m];

		}
		// substitui o array com os valores dentro do intervalo jah ordenados dentro do arrayCopia
		completaArray(array, novoArray, leftIndex, rightIndex);

		}
	}

	// Metodo que substitui os valores dentro do array original
	private void completaArray(Integer[] array1, Integer[] array2, int leftIndex, int rightIndex) {
		for (int i = leftIndex; i <= rightIndex; i++) {
			array1[i] = array2[i - leftIndex];
		}
	}

	// metodo para encontrar o menor e o maior valor dentro do array
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
