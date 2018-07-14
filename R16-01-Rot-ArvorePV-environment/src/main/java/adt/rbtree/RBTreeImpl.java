package adt.rbtree;


import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return 0; //implementar
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour()
				&& verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
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
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	protected boolean verifyChildrenOfRedNodes() {
		return true; //implementar
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		return true; //implementar
	}

	@Override
	public void insert(T value) {
		// implementar
	}


	@Override
	public RBNode<T>[] rbPreOrder() {
		return null; //implementar
	
	}


	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
	}

	protected void fixUpCase2(RBNode<T> node) {
		
	}

	protected void fixUpCase3(RBNode<T> node) {

	}

	protected void fixUpCase4(RBNode<T> node) {

	}

	protected void fixUpCase5(RBNode<T> node) {
	
	}
}
