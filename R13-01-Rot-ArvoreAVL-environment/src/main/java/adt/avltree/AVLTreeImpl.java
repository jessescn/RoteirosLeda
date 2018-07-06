package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return -1;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);

		if (Math.abs(balance) > 1) {

			if (balance > 0) {

				if (calculateBalance((BSTNode<T>) node.getLeft()) < 0) {
					this.leftRotation((BSTNode<T>) node.getLeft());
				}
				this.rightRotation(node);

			} else {

				if (calculateBalance((BSTNode<T>) node.getRight()) > 0) {

					this.rightRotation((BSTNode<T>) node.getRight());

				}
				this.leftRotation(node);
			}
		}
	}

	private void leftRotation(BSTNode<T> node) {
		if (node.equals(this.root)) {
			BSTNode<T> aux = Util.leftRotation(node);
			this.root = aux;
		} else {
			BSTNode<T> aux = Util.leftRotation(node);
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}

	}

	private void rightRotation(BSTNode<T> node) {
		if (node.equals(this.root)) {
			BSTNode<T> aux = Util.rightRotation(node);
			this.root = aux;
		} else {
			BSTNode<T> aux = Util.rightRotation(node);
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}

	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null) {
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
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
			if (element.compareTo(node.getData()) < 0) {
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
		rebalance(node);
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				if (!node.equals(root)) {
					putLocal((BSTNode<T>) node.getParent(), node, new BSTNode<>());
				} else {
					this.root = new BSTNode<T>();
				}

				rebalanceUp(node);
			} else {

				if ((node.getLeft().isEmpty() || node.getRight().isEmpty())) {

					if (node.getLeft().isEmpty()) {
						putLocal((BSTNode<T>) node.getParent(), node, (BSTNode<T>) node.getRight());
					} else {
						putLocal((BSTNode<T>) node.getParent(), node, (BSTNode<T>) node.getLeft());
					}

					rebalanceUp(node);
				} else {
					if(sucessor(element) != null) {
						T elemento = sucessor(element).getData();
						remove(elemento);
						node.setData(elemento);
					}
				}
			}
		}
	}
}
