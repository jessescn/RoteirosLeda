package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em suas funcionalidades
 * e possui um metodo de ordenar um array dado como parametro, retornando o resultado do percurso
 * desejado que produz o array ordenado. 
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;
	
	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		for(T el : array) {
			insert(el);
		}
		return order();
	}

	@Override
	public T[] reverseOrder() {
		T[] array = (T[]) new Comparable[size()];
		return reverseOrder(this.root, array);
	}
	
	private T[] reverseOrder(BSTNode<T> node, T[] array) {
		if(!isEmpty()) {
			if(!node.getRight().isEmpty()) {
				reverseOrder((BSTNode<T>) node.getRight(), array);
			}
			push(array, node.getData());
			if(!node.getLeft().isEmpty()) {
				reverseOrder((BSTNode<T>) node.getLeft(), array);
			}	
		}
		return array;
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(element, this.root);
	}

	private BSTNode<T> search(T element, BSTNode<T> node) {
		if (!node.isEmpty()) {
			if (this.comparator.compare(element, node.getData()) == 0) {
				return node;
			} else {
				if (this.comparator.compare(element, node.getData()) < 0) {
					return search(element, (BSTNode<T>) node.getLeft());
				} else {
					return search(element, (BSTNode<T>) node.getRight());
				}
			}
		}
		return new BSTNode<T>();
	}
	
	@Override
	public void insert(T element) {
		insert(element, this.root);
	}

	private void insert(T element, BSTNode<T> node) {
		BSTNode<T> nil = new BSTNode<T>();
		if (isEmpty()) {
			this.root = new BSTNode.Builder().data(element).right(nil).left(nil).build();
		} else {
			if (this.comparator.compare(element, node.getData()) < 0) {
				if (!node.getLeft().isEmpty()) {
					insert(element, (BSTNode<T>) node.getLeft());
				} else {
					node.setLeft(new BSTNode.Builder().data(element).parent(node).left(nil).right(nil).build());
				}
			} else {
				if (!node.getRight().isEmpty()) {
					insert(element, (BSTNode<T>) node.getRight());
				} else {
					node.setRight(new BSTNode.Builder().data(element).parent(node).left(nil).right(nil).build());
				}
			}
		}
	}
	

	
	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
}
