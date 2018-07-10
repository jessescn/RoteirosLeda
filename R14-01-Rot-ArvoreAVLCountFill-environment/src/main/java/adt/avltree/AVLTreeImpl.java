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
		int height = -1;
		if (!node.isEmpty()) {
			if (node instanceof BSTNode)
				height = height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return height;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = this.calculateBalance(node);

		if (Math.abs(balance) > 1) {
			if (node instanceof BSTNode) {

				
				if (balance > 1) {

			
					if (calculateBalance((BSTNode<T>) node.getLeft()) < 0) {
						this.leftRotation((BSTNode<T>) node.getLeft());
					}

	
					this.rightRotation(node);

				} else if (balance < -1) {

					if (calculateBalance((BSTNode<T>) node.getRight()) > 0) {
						this.rightRotation((BSTNode<T>) node.getRight());
					}

					this.leftRotation(node);
				}
			}
		}
	}


	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node instanceof BSTNode && node.getParent() instanceof BSTNode) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			while (parent != null) {
				rebalance(parent);
				parent = (BSTNode<T>) parent.getParent();
			}
		}
	}

	@Override
	public void insert(T element) {
		if (element != null)
			insert(element, this.root);
	}

	private void insert(T element, BSTNode<T> node) {
		BSTNode<T> nil = new BSTNode<T>();

		if (this.isEmpty()) {
			this.root = new BSTNode.Builder().data(element).right(nil).left(nil).build();

		} else {


			if (element.compareTo(node.getData()) <= 0) {

		
				if (node.getLeft().isEmpty()) {
					node.setLeft(new BSTNode.Builder().data(element).parent(node).left(nil).right(nil).build());

				} else {
					if (node.getLeft() instanceof BSTNode)
						insert(element, (BSTNode<T>) node.getLeft());
				}


			} else {

				if (node.getRight().isEmpty()) {
					node.setRight(new BSTNode.Builder().data(element).parent(node).left(nil).right(nil).build());

				} else {
					if (node.getRight() instanceof BSTNode)
						insert(element, (BSTNode<T>) node.getRight());
				}
			}
		}

		this.rebalance(node);
	}

	

	@Override
	public void remove(T element) {
		if (element != null) {

			BSTNode<T> node = search(element);

			if (!node.isEmpty()) {

				if (node.isLeaf()) {
					this.noSon(node);

				} else {

					if ((node.getLeft().isEmpty() || node.getRight().isEmpty())) {
						this.oneSon(node);

					} else {
						this.twoSons(node, element);
					}
				}

	
				rebalanceUp(node);
			}
		}
	}
	
	/**
	 * Metodo auxiliar do remove quando o node nao possui filho (eh uma folha), que
	 * somente seta o data como null, ou seja, o node passa a ser NIL
	 * 
	 * @param node
	 */
	private void noSon(BSTNode<T> node) {
		node.setData(null);
	}

	/**
	 * Metodo auxiliar do remove quando o node possui apenas um filho, fazendo a
	 * ligacao do parent do node com o left ou o right nao vazios, caso o node seja
	 * a raiz, o filho assume a referencia da raiz
	 * 
	 * @param node
	 */
	private void oneSon(BSTNode<T> node) {
		if (node.getParent() instanceof BSTNode) {

			if (!node.equals(this.root)) {

				if (node.getLeft().isEmpty()) {
					if (node.getRight() instanceof BSTNode)
	
						putLocal((BSTNode<T>) node.getParent(), node, (BSTNode<T>) node.getRight());

				} else {
					if (node.getLeft() instanceof BSTNode)
						putLocal((BSTNode<T>) node.getParent(), node, (BSTNode<T>) node.getLeft());
				}

			} else {

				if (this.root.getRight().isEmpty()) {
					if (node.getLeft() instanceof BSTNode)
						this.root = (BSTNode<T>) node.getLeft();
				} else {
					if (node.getRight() instanceof BSTNode)
						this.root = (BSTNode<T>) node.getRight();
				}
			}
		}
	}

	/**
	 * Metodo auxiliar para o remove quando o node possui dois filhos, que recebe o
	 * node e o elemento, removendo o mesmo e colocando seu sucessor em seu lugar
	 * 
	 * @param node
	 * @param element
	 */
	private void twoSons(BSTNode<T> node, T element) {
		if (this.sucessor(element) != null) {

			BSTNode<T> sucessor = this.sucessor(element);
			T data = sucessor.getData();
			remove(sucessor.getData());
			node.setData(data);
		}
	}
	
	/**
	 * Metodo auxiliar da leftRotation da classe Util tanto para verificar no caso
	 * do node ser a raiz, como para atualizar os apontadores do novo node (node que
	 * antes era o filho passando a ser o parent) do parent pro node e do node pro
	 * parent
	 * 
	 * @param node
	 */
	protected void leftRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.leftRotation(node);

		if (node.equals(this.root)) {
			this.root = aux;
		} else {
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}
	}

	/**
	 * Metodo auxiliar da RightRotation da classe Util tanto para verificar no caso
	 * do node ser a raiz, como para atualizar os apontadores do novo node (node que
	 * antes era o filho passando a ser o parent) do parent pro node e do node pro
	 * parent
	 * 
	 * @param node
	 */
	protected void rightRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.rightRotation(node);

		if (node == this.root) {
			this.root = aux;
		} else {
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}

	}

}