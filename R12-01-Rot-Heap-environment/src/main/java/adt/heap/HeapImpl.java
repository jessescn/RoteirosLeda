package adt.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita
 * diretamente com os elementos armazenados, mas sim usando um comparator. Dessa
 * forma, dependendo do comparator, a heap pode funcionar como uma max-heap ou
 * min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse comparator.
	 * Assim os metodos da heap não precisam saber se vai funcionar como max-heap
	 * ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento indexado
	 * pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento indexado
	 * pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		ArrayList<T> resp = new ArrayList<T>();
		for (T elem : this.heap) {
			if (elem != null) {
				resp.add(elem);
			}
		}
		return (T[]) resp.toArray(new Comparable[0]);
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode ser
	 * a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		if (!isEmpty() && position < this.index + 1) {
			int max = indexMax(position, left(position), right(position));
			if (max != position) {
				Util.swap(heap, position, max);
				heapify(max);
			}
		}
	}

	private int indexMax(int position, int left, int right) {
		int max = position;
		if (left < this.size()) {
			if (this.comparator.compare(heap[max], heap[left]) < 0) {
				max = left;
			}
			if (right < this.size()) {
				if (this.comparator.compare(heap[max], heap[right]) < 0) {
					max = right;
				}
			}
		}
		return max;
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////
		if (element != null) {
			this.index++;
			this.heap[index] = element;
			int i = this.index;
			
			while (i > 0 && this.comparator.compare(this.heap[parent(i)], this.heap[i]) < 0) {
				Util.swap(this.heap, this.parent(i), i);
				i = this.parent(i);

			}
		}
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = array.length - 1;
		for (int i = parent(index); i >= 0; i--) {
			this.heapify(i);
		}

	}

	@Override
	public T extractRootElement() {
		T max = null;
		
		if (!isEmpty()) {
			
			max = this.rootElement();
			Util.swap(this.heap, this.index, 0);
			this.heap[index] = null;
			this.index--;
			this.heapify(0);

		}
		return max;
	}

	@Override
	public T rootElement() {
		T root = null;
		if (!isEmpty())
			root = this.heap[0];
		return root;
	}

	@Override
	public T[] heapsort(T[] array) {
		T[] saida = null;
		if (array != null) {
			Comparator<T> comp = this.comparator;
			this.comparator = new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					return o1.compareTo(o2);
				}
			};		
			this.buildHeap(array);
			saida = (T[]) new Comparable[this.size()];	
			
			for (int i = this.index; i >= 0; i--)
				saida[i] = this.extractRootElement();
			
			this.comparator = comp;
		}
		return saida;
	}

	@Override
	public int size() {
		return index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
