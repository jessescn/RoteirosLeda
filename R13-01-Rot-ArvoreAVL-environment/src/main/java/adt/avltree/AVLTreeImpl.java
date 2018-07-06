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
			if (node instanceof BSTNode)
				return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return -1;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = this.calculateBalance(node);

		// Testa se a avl no node esta desbalanceada, ou seja, se o modulo do balance eh
		// maior que 1
		if (Math.abs(balance) > 1) {
			if (node instanceof BSTNode) {

				// Se o balance for maior que 1, ela esta desbalanceada pra direita
				if (balance > 1) {

					// Se o balance do left for menor que zero, cai no caso do zigue zague RL, sendo
					// necessario rotacionar primeiramente para a esquerda, depois pra direita
					if (calculateBalance((BSTNode<T>) node.getLeft()) < 0) {
						this.leftRotation((BSTNode<T>) node.getLeft());
					}

					// Rotaciona pra direita
					this.rightRotation(node);

					// Se o balance for menor que -1, ela esta desbalanceada pra esquerda
				} else if (balance < -1) {

					// Se o balance do right for maior que zero, cai no caso do zigue zague LR,
					// sendo
					// necessario rotacionar primeiramente para a direita, depois pra esquerda
					if (calculateBalance((BSTNode<T>) node.getRight()) > 0) {
						this.rightRotation((BSTNode<T>) node.getRight());
					}

					// Rotaciona pra esquerda
					this.leftRotation(node);
				}
			}
		}
	}


	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node instanceof BSTNode && node.getParent() instanceof BSTNode) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			// enquanto o parent != null, ou seja, o node nao for a raiz, ele vai dando
			// rebalance do pai do node
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
		// Se a bst estiver vazia
		if (this.isEmpty()) {
			this.root = new BSTNode.Builder().data(element).right(nil).left(nil).build();

		} else {

			// Se o elemento for menor que o node, ele deve adicionado a arvore a esquerda
			if (element.compareTo(node.getData()) < 0) {

				// Se o left for vazio, esta na hora de adicionar o novo elemento
				if (node.getLeft().isEmpty()) {
					node.setLeft(new BSTNode.Builder().data(element).parent(node).left(nil).right(nil).build());

					// Se o left do node nao for vazio, deve haver a chamada recursiva do insert pro
					// left node
				} else {
					if (node.getLeft() instanceof BSTNode)
						insert(element, (BSTNode<T>) node.getLeft());
				}

				// Se o elemento for maior que o node, ele deve ser adicionado a arvore a
				// direita
			} else {

				// Se o elemento a direita for vazio, o node deve ser adicionado
				if (node.getRight().isEmpty()) {
					node.setRight(new BSTNode.Builder().data(element).parent(node).left(nil).right(nil).build());

					// Se o right do node nao for vazio, deve haver a chamada recursiva do insert
					// pro right node
				} else {
					if (node.getRight() instanceof BSTNode)
						insert(element, (BSTNode<T>) node.getRight());
				}
			}
		}

		// Apos o insert, deve ser chamado o metodo rebalance a partir do novo node para
		// balancear possiveis desbalancos devido ao insert do novo elemento

		this.rebalance(node);
	}

	

	@Override
	public void remove(T element) {
		if (element != null) {

			BSTNode<T> node = search(element);

			// Se o node retornado do metodo search for diferente de vazio (o elemento esta
			// contido na bst)
			if (!node.isEmpty()) {

				// Se o node for uma folha
				if (node.isLeaf()) {
					this.noSon(node);

				} else {

					// Se o node tiver apenas uma folha
					if ((node.getLeft().isEmpty() || node.getRight().isEmpty())) {
						this.oneSon(node);

						// Se nao, ou seja, se ele possuir as duas folhas
					} else {
						this.twoSons(node, element);
					}
				}

				// Apos a remocao, eh chamado o metodo para rebalancear a avl, do node subindo
				// ate a raiz
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

			// Se o node nao for o root
			if (!node.equals(this.root)) {

				// Se o seu left for vazio (ou seja, que seu filho eh o right)
				if (node.getLeft().isEmpty()) {
					if (node.getRight() instanceof BSTNode)
						// Chama o metodo putLocal que eh responsavel por fazer a ligacao do parent do
						// node com o filho do node
						putLocal((BSTNode<T>) node.getParent(), node, (BSTNode<T>) node.getRight());

					// Se o seu right for vazio( ou seja, o seu filho eh o left)
				} else {
					if (node.getLeft() instanceof BSTNode)
						putLocal((BSTNode<T>) node.getParent(), node, (BSTNode<T>) node.getLeft());
				}

				// Caso o node seja a raiz, ele atualiza a referencia da raiz pra o novo root
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
	private void leftRotation(BSTNode<T> node) {
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
	private void rightRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.rightRotation(node);

		if (node.equals(this.root)) {
			this.root = aux;
		} else {
			putLocal((BSTNode<T>) aux.getParent(), node, aux);
		}

	}

}