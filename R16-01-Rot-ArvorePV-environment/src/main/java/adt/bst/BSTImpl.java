package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

   protected BSTNode<T> root;

   public BSTImpl() {
      root = new BSTNode<T>();
   }

   public BSTNode<T> getRoot() {
      return this.root;
   }

   @Override
   public boolean isEmpty() {
      return root.isEmpty();
   }

   @Override
   public int height() {
      return height(this.root);
   }

   public int height(BSTNode<T> node) {
      if (node.isEmpty())
         return -1;
      else {
         int hleft = height((BSTNode<T>) node.getLeft());
         int hright = height((BSTNode<T>) node.getRight());
         if (hleft < hright) {
            return hright + 1;
         } else {
            return hleft + 1;
         }
      }

   }

   @Override
   public BSTNode<T> search(T element) {
      return search(element, this.root);
   }

   private BSTNode<T> search(T element, BSTNode<T> node) {
      if (!node.isEmpty()) {
         if (element.equals(node.getData())) {
            return node;
         } else {
            if (element.compareTo(node.getData()) < 0) {
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
		if (element != null)
			insert(element, this.root);
	}

	protected void insert(T element, BSTNode<T> node) {
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
	}


   @Override
   public BSTNode<T> maximum() {
      if (!isEmpty()) {
         return maximum(this.root);
      }
      return null;
   }

   public BSTNode<T> maximum(BSTNode<T> node) {
      if (!node.getRight().isEmpty()) {
         return maximum((BSTNode<T>) node.getRight());
      }
      return node;
   }

   @Override
   public BSTNode<T> minimum() {
      if (!isEmpty()) {
         return minimum(this.root);
      }
      return null;
   }

   public BSTNode<T> minimum(BSTNode<T> node) {
      if (!node.getLeft().isEmpty()) {
         return minimum((BSTNode<T>) node.getLeft());
      }
      return node;
   }

   @Override
   public BSTNode<T> sucessor(T element) {
      BSTNode<T> node = search(element);
      if (!node.isEmpty()) {
         return this.sucessor(node);
      }
      return null;

   }

   private BSTNode<T> sucessor(BSTNode<T> node) {
      if (!node.getRight().isEmpty()) {
         return this.minimum((BSTNode<T>) node.getRight());
      } else {
         BSTNode<T> auxA = node;
         BSTNode<T> auxB = (BSTNode<T>) node.getParent();
         while (auxB != null && auxA == auxB.getRight()) {
            auxA = auxB;
            auxB = (BSTNode<T>) auxB.getParent();
         }
         return auxB;
      }
   }

   @Override
   public BSTNode<T> predecessor(T element) {
      BSTNode<T> node = search(element);
      if (!node.isEmpty()) {
         return predecessor(node);
      }
      return null;
   }

   private BSTNode<T> predecessor(BSTNode<T> node) {
      if (!node.getLeft().isEmpty()) {
         return maximum((BSTNode<T>) node.getLeft());
      } else {
         BSTNode<T> auxA = node;
         BSTNode<T> auxB = (BSTNode<T>) node.getParent();
         while (auxB != null && auxA == auxB.getLeft()) {
            auxA = auxB;
            auxB = (BSTNode<T>) auxB.getParent();
         }
         return auxB;
      }
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

			}
		}
	}

	private void noSon(BSTNode<T> node) {
		node.setData(null);
	}

	
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

	
	private void twoSons(BSTNode<T> node, T element) {
		if (this.sucessor(element) != null) {

			BSTNode<T> sucessor = this.sucessor(element);
			T data = sucessor.getData();
			remove(sucessor.getData());
			node.setData(data);
		}
	}

   public void putLocal(BSTNode<T> parent, BSTNode<T> node, BSTNode<T> newNode) {

      if (parent != null) {
         if (parent.getLeft().equals(node)) {
            parent.setLeft(newNode);
            newNode.setParent(parent);
         } else {
            parent.setRight(newNode);
            newNode.setParent(parent);
         }
      }
   }

   @Override
   public T[] preOrder() {
      T[] array = (T[]) new Comparable[size()];
      if (!isEmpty()) {
         return preOrder(this.root, array);
      }
      return array;
   }

   private T[] preOrder(BSTNode<T> node, T[] array) {
      if (!isEmpty()) {
         push(array, node.getData());
         if (!node.getLeft().isEmpty())
            preOrder((BSTNode<T>) node.getLeft(), array);
         if (!node.getRight().isEmpty())
            preOrder((BSTNode<T>) node.getRight(), array);
      }
      return array;
   }

   private void push(T[] array, T element) {
      int i = 0;
      while (array[i] != null) {
         i++;
      }
      if (array[i] == null) {
         array[i] = element;
      }
   }

   @Override
   public T[] order() {
      T[] array = (T[]) new Comparable[size()];
      return order(this.root, array);
   }

   private T[] order(BSTNode<T> node, T[] array) {
      if (!isEmpty()) {
         if (!node.getLeft().isEmpty())
            order((BSTNode<T>) node.getLeft(), array);
         push(array, node.getData());
         if (!node.getRight().isEmpty())
            order((BSTNode<T>) node.getRight(), array);
      }
      return array;
   }

   @Override
   public T[] postOrder() {
      T[] array = (T[]) new Comparable[size()];
      return postOrder(this.root, array);
   }

   private T[] postOrder(BSTNode<T> node, T[] array) {
      if (!isEmpty()) {
         if (!node.getLeft().isEmpty())
            postOrder((BSTNode<T>) node.getLeft(), array);
         if (!node.getRight().isEmpty())
            postOrder((BSTNode<T>) node.getRight(), array);
         push(array, node.getData());
      }
      return array;
   }

   /**
    * This method is already implemented using recursion. You must understand how
    * it work and use similar idea with the other methods.
    */
   @Override
   public int size() {
      return size(root);
   }

   private int size(BSTNode<T> node) {
      int result = 0;
      // base case means doing nothing (return 0)
      if (!node.isEmpty()) { // indusctive case
         result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
      }
      return result;
   }

}
