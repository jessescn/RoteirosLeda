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
		return 0; // implementar
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
		return true; // implementar
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		return true; // implementar
	}

	@Override
	public void insert(T element) {
		
		if (element != null)
			insert(element, this.root);
		RBNode<T> node = (RBNode<T>) search(element);
		fixUpCase1(node);
	}
	

	protected RBNode<T> insert(T element, RBNode<T> node) {
		RBNode<T> nil = new RBNode<T>();
		
		RBNode<T> newNode = new RBNode<>();
		newNode.setColour(Colour.RED);
		newNode.setData(element);
		newNode.setLeft(nil);
		newNode.setRight(nil);

		if (this.isEmpty()) {
			this.root = newNode;
			
		} else {
			if (element.compareTo(node.getData()) <= 0) {
				newNode.setParent(node);
				if (node.getLeft().isEmpty()) {
					node.setLeft(newNode);
				
				} else {
					if (node.getLeft() instanceof RBNode)
						insert(element, (RBNode<T>) node.getLeft());
				}

			} else {
				if (node.getRight().isEmpty()) {
					node.setRight(newNode);
				} else {
					if (node.getRight() instanceof RBNode)
						insert(element, (RBNode<T>) node.getRight());
				}
			}
		}
		return newNode;
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
	            rbPreOrder( (RBNode<T>) node.getRight(), array);
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
		node.setColour(Colour.RED);
		if(node == this.root) {
			node.setColour(Colour.BLACK);
		}else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if(((RBNode<T>) node.getParent()).getColour() != Colour.BLACK) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		if(((RBNode<T>) getUncle(node)).getColour() == Colour.RED) {
			
			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			((RBNode<T>) getUncle(node)).setColour(Colour.BLACK);
			RBNode<T> grandma = (RBNode<T>) node.getParent().getParent();
			grandma.setColour(Colour.RED);
			fixUpCase1(grandma);
			
		}else {
			fixUpCase4(node);
		}
	}
	
	private BTNode<T> getUncle(RBNode<T> node) {
		BTNode<T> parent = node.getParent();
		BTNode<T> pParent = parent.getParent();
		BTNode<T> uncle = null;
		
		if(parent == pParent.getLeft()) {
			uncle = pParent.getRight();
		}else {
			uncle = pParent.getLeft();
		}
		return uncle;
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		
		if(LR(node)) {
			Util.leftRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
			
		}else if(RL(node)) {
			Util.rightRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}
		
		fixUpCase5(next);

	}
	
	private boolean LR(RBNode<T> node) {
		boolean isLR = false;
		BTNode<T> parent = node.getParent();
		BTNode<T> grandma = parent.getParent();
		
		if(grandma.getLeft() == parent && parent.getRight() == node) {
			isLR = true;
		}	
		return isLR;
	}
	
	private boolean RL(RBNode<T> node) {
		boolean isRL = false;
		BTNode<T> parent = node.getParent();
		BTNode<T> grandma = parent.getParent();
		
		if(grandma.getRight() == parent && parent.getLeft() == node) {
			isRL = true;
		}
		return isRL;
	}

	protected void fixUpCase5(RBNode<T> node) {
		
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandma = (RBNode<T>) parent.getParent();
		
		parent.setColour(Colour.BLACK);
		grandma.setColour(Colour.RED);
		
		if(parent.getLeft() == node) {
			Util.rightRotation(grandma);
			
		}else {
			Util.leftRotation(grandma);
		}

	}
}
