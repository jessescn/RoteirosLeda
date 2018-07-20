package adt.btree;

import java.util.ArrayList;
import java.util.LinkedList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		int height = -1;
		
		if (!node.isEmpty()) {
			height += 1;
		}

		if (!node.isLeaf()) {
			BNode<T> aux = node.getChildren().get(0);
			for(BNode<T> a : node.getChildren()) {
				if(!a.isEmpty()) {
					aux = a;
				}
			}
			
			height += 1 + this.height(aux);
		}
		return height;
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		ArrayList<BNode<T>> array = new ArrayList<>();

		this.depthLeftOrder(array, this.root);

		BNode<T>[] resultReturn = new BNode[array.size()];

		for (int i = 0; i < array.size(); i++) {
			resultReturn[i] = array.get(i);
		}

		return resultReturn;
	}

	private void depthLeftOrder(ArrayList<BNode<T>> array, BNode<T> node) {
		if (node != null) {
			array.add(node);

			LinkedList<BNode<T>> children = node.getChildren();

			for (BNode<T> aux : children) {
				this.depthLeftOrder(array, aux);
			}
		}

	}

	@Override
	public int size() {
		return this.size(this.root);
	}

	private int size(BNode<T> node) {
		int totalSize = 0;

		totalSize += node.size();

		LinkedList<BNode<T>> children = node.getChildren();

		for (BNode<T> aux : children) {
			totalSize += this.size(aux);
		}

		return totalSize;

	}

	@Override
	public BNodePosition<T> search(T element) {
		BNodePosition<T> result = new BNodePosition<T>();
		if (!this.isEmpty())
			result = search(this.root, element);
		return result;
	}

	private BNodePosition<T> search(BNode<T> node, T element) {
		BNodePosition<T> result = new BNodePosition<>();
		
		if (node != null) {
			
			int index = 0;

			while (index < node.size() && element.compareTo(node.getElementAt(index)) > 0) {
				index += 1;
			}

			if (index < node.size() && element.equals(node.getElementAt(index))) {
				result.node = node;
				result.position = index;

			} else {
				result = this.search(node.getChildren().get(index), element);
			}
		}

		return result;

	}

	@Override
	public void insert(T element) {
		if (element != null)
			insert(this.root, element);
	}

	private void insert(BNode<T> node, T element) {

		if (node.isLeaf()) {
			if (node.isFull()) {
				node.addElement(element);
				split(node);
			} else {
				node.addElement(element);
			}

		} else {

			int index = 0;

			while (index < node.size() && element.compareTo(node.getElementAt(index)) > 0) {
				index += 1;
			}

			insert(node.getChildren().get(index), element);
		}

	}

	private void split(BNode<T> node) {
		
		int half = this.order/ 2;
		T middleValue = node.getElementAt(half);
	
		BNode<T> left = node.getFirstHalf();
		left.setParent(node.getParent());
		BNode<T> right = node.getSecondHalf();
		right.setParent(node.getParent());

		node = this.promote(node, left, right, middleValue);
		
		if(node.getElements().size() >= this.order) {
			this.split(node);
		}
		

	}

	private BNode<T> promote(BNode<T> node, BNode<T> left, BNode<T> right, T aux) {
		BNode<T> returnNode;

		if (node.equals(this.root)) {

			BNode<T> newRoot = new BNode<>(this.order);
			newRoot.addChild(0, left);
			newRoot.addChild(1, right);
			newRoot.addElement(aux);

			this.root = newRoot;
			returnNode = newRoot;
			

		} else {
			BNode<T> parent = node.getParent();
			parent.addElement(aux);
			BNodePosition<T> apontador = this.search(aux);

			LinkedList<BNode<T>> children = parent.getChildren();

			children.set(apontador.position, left);
			children.add(apontador.position + 1, right);

			returnNode = node.getParent();
		}

		return returnNode;
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
