package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionDivisionMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import adt.hashtable.hashfunction.HashFunctionMultiplicationMethod;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size of
	 * the table to an integer that is prime. This can be achieved by producing such
	 * a prime number that is bigger and close to the desired size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and getPrimeAbove as
	 * documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number greater
	 * than the given size (or the given size, if it is already prime). For example,
	 * if size=10 then the length must be 11. If size=20, the length must be 23. You
	 * must implement this idea in the auxiliary method getPrimeAbove(int size) and
	 * use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
			// the immediate prime
			// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. If the given number is prime, it is returned. You can use the method
	 * Util.isPrime to check if a number is prime.
	 */
	int getPrimeAbove(int number) {
		int nextPrime = number;
		while (!Util.isPrime(nextPrime)) {
			nextPrime++;
		}
		return nextPrime;
	}

	@Override
	public void insert(T element) {
		if (search(element) == null) {
			if (this.table[hashFunction(element)] == null) {
				LinkedList<T> elements = new LinkedList<T>();
				elements.add(element);
				this.table[hashFunction(element)] = elements;
				this.elements++;
			} else {
				LinkedList<T> elements = (LinkedList<T>) this.table[hashFunction(element)];
				if (!contains(element, elements)) {
					elements.add(element);
					this.elements++;
					this.COLLISIONS++;
				}
			}
		}
	}

	private int hashFunction(T element) {
		if(hashFunction instanceof HashFunctionDivisionMethod) {
			return ((HashFunctionDivisionMethod<T>) this.hashFunction).hash(element);
		}else {
			return ((HashFunctionMultiplicationMethod<T>) this.hashFunction).hash(element);
		}
	}

	@Override
	public void remove(T element) {
		if (search(element) != null) {
			LinkedList<T> elements = (LinkedList<T>) table[hashFunction(element)];
			elements.remove(element);
			this.elements--;
		}
	}

	@Override
	public T search(T element) {
		LinkedList<T> elements = (LinkedList<T>) table[hashFunction(element)];
		if (elements != null && contains(element, elements)) {
			return element;
		} else {
			return null;
		}
	}

	@Override
	public int indexOf(T element) {
		if (search(element) != null) {
			return hashFunction(element);
		} else {
			return -1;
		}
	}

	private boolean contains(T element, LinkedList lista) {
		boolean contain = false;
		for (int i = 0; i < lista.size(); i++) {
			if ((int) lista.get(i) == (int) element) {
				contain = true;
			}
		}
		return contain;
	}

}
