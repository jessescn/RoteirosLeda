package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();

	}

	protected int blackHeight() {
		int bHeight = 0;
		if (!this.isEmpty()) {
			bHeight = blackHeight((RBNode<T>) this.root);
		}
		return bHeight;
	}

	private int blackHeight(RBNode<T> node) {
		int bHeight = 0;
		int bRight = 0;
		int bLeft = 0;

		if (node != null && !node.isEmpty()) {
			if (this.isBlack(node)) {
				bHeight++;
			}

			bRight = blackHeight((RBNode<T>) node.getRight());
			bLeft = blackHeight((RBNode<T>) node.getLeft());
		}

		return bHeight + Math.max(bRight, bLeft);
	}

	private boolean isBlack(RBNode<T> node) {
		return node.getColour() == Colour.BLACK;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed by
	 * the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must be
	 * BLACK.
	 */
	protected boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNode((RBNode<T>) this.root);
	}

	private boolean verifyChildrenOfRedNode(RBNode<T> node) {
		boolean result = true;

		if (!node.isEmpty()) {
			if (this.isBlack(node)) {
				result = this.verifyChildrenOfRedNode((RBNode<T>) node.getLeft())
						&& this.verifyChildrenOfRedNode((RBNode<T>) node.getRight());
			} else {
				if (this.isBlack((RBNode<T>) node.getLeft()) && this.isBlack((RBNode<T>) node.getRight())) {
					result = this.verifyChildrenOfRedNode((RBNode<T>) node.getLeft())
							&& this.verifyChildrenOfRedNode((RBNode<T>) node.getRight());
				} else {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		return this.blackHeight((RBNode<T>) this.root.getLeft()) == this.blackHeight((RBNode<T>) this.root.getRight());

	}

	@Override
	public void insert(T element) {
		if (element != null)
			insert(element, (RBNode<T>) this.root);
	}

	private void insert(T element, RBNode<T> node) {

		if (this.isEmpty()) {
			this.root = new RBNode<T>();
			setNewNode((RBNode<T>) this.root, element);
			fixUpCase1((RBNode<T>) this.root);

		} else {
			if (element.compareTo(node.getData()) < 0) {

				if (node.getLeft().isEmpty()) {
					node.setLeft(new RBNode<T>());
					setNewNode((RBNode<T>) node.getLeft(), element);
					node.getLeft().setParent(node);
					fixUpCase1((RBNode<T>) node.getLeft());

				} else {
					insert(element, (RBNode<T>) node.getLeft());
				}

			} else {

				if (node.getRight().isEmpty()) {
					node.setRight(new RBNode<T>());
					setNewNode((RBNode<T>) node.getRight(), element);
					node.getRight().setParent(node);
					fixUpCase1((RBNode<T>) node.getRight());

				} else {
					insert(element, (RBNode<T>) node.getRight());
				}
			}
		}
	}

	private void setNewNode(RBNode<T> node, T element) {
		node.setData(element);
		node.setLeft(new RBNode<T>());
		node.setRight(new RBNode<T>());
		node.setColour(Colour.RED);
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		RBNode<T>[] array = new RBNode[this.size()];
		return rbPreOrder((RBNode<T>) this.root, array);

	}

	private RBNode<T>[] rbPreOrder(RBNode<T> node, RBNode<T>[] array) {
		if (!isEmpty()) {
			push(array, node);
			if (!node.getLeft().isEmpty())
				rbPreOrder((RBNode<T>) node.getLeft(), array);
			if (!node.getRight().isEmpty())
				rbPreOrder((RBNode<T>) node.getRight(), array);
		}
		return array;
	}

	private void push(RBNode<T>[] array, RBNode<T> node) {
		int i = 0;
		while (array[i] != null) {
			i++;
		}
		if (array[i] == null) {
			array[i] = node;
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.getParent() == null) {

			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}

	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!this.isBlack((RBNode<T>) node.getParent())) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		if (((RBNode<T>) getUncle(node)).getColour() == Colour.RED) {

			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			((RBNode<T>) getUncle(node)).setColour(Colour.BLACK);
			RBNode<T> grandma = (RBNode<T>) node.getParent().getParent();
			grandma.setColour(Colour.RED);

			fixUpCase1(grandma);

		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;

		if (isLR(node)) {
			this.leftRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();

		} else if (isRL(node)) {
			this.rightRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}

		fixUpCase5(next);

	}

	protected void fixUpCase5(RBNode<T> node) {

		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandma = (RBNode<T>) parent.getParent();

		parent.setColour(Colour.BLACK);
		grandma.setColour(Colour.RED);

		if (parent.getLeft().equals(node)) {
			this.rightRotation(grandma);

		} else {
			this.leftRotation(grandma);
		}

	}

	private boolean isLR(RBNode<T> node) {
		boolean isLR = false;
		BTNode<T> parent = node.getParent();
		BTNode<T> grandma = parent.getParent();

		if (grandma.getLeft().equals(parent) && parent.getRight().equals(node)) {
			isLR = true;
		}
		return isLR;
	}

	private boolean isRL(RBNode<T> node) {
		boolean isRL = false;
		BTNode<T> parent = node.getParent();
		BTNode<T> grandma = parent.getParent();

		if (grandma.getRight().equals(parent) && parent.getLeft().equals(node)) {
			isRL = true;
		}
		return isRL;
	}

	private BTNode<T> getUncle(RBNode<T> node) {
		BTNode<T> parent = node.getParent();
		BTNode<T> grandma = parent.getParent();
		BTNode<T> uncle = null;

		if (grandma.getLeft().equals(parent)) {
			uncle = grandma.getRight();
		} else {
			uncle = grandma.getLeft();
		}
		return uncle;
	}

	private void leftRotation(RBNode<T> node) {
		BSTNode<T> aux = Util.leftRotation(node);

		if (node.equals(this.root)) {
			this.root = aux;
		} else {
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}
	}

	private void rightRotation(RBNode<T> node) {
		BSTNode<T> aux = Util.rightRotation(node);

		if (node == this.root) {
			this.root = aux;
		} else {
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}

	}
}
