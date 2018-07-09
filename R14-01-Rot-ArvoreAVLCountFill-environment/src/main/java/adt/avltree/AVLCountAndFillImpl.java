package adt.avltree;

import adt.bst.BSTNode;


public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

   private int LLcounter;
   private int LRcounter;
   private int RRcounter;
   private int RLcounter;

   public AVLCountAndFillImpl() {

   }

   @Override
   public int LLcount() {
      return LLcounter;
   }

   @Override
   public int LRcount() {
      return LRcounter;
   }

   @Override
   public int RRcount() {
      return RRcounter;
   }

   @Override
   public int RLcount() {
      return RLcounter;
   }

   @Override
   protected void rebalance(BSTNode<T> node) {
      int balance = this.calculateBalance(node);

      if (Math.abs(balance) > 1) {
         if (node instanceof BSTNode) {
            boolean notZZ = true;

            if (balance > 1) {

               notZZ = true;

               if (calculateBalance((BSTNode<T>) node.getLeft()) < 0) {
                  notZZ = false;
                  this.leftRotation((BSTNode<T>) node.getLeft());
               }
               this.rightRotation(node);

               if (notZZ) {
                  this.LLcounter++;
               } else {
                  this.LRcounter++;
               }

            } else if (balance < -1) {
               notZZ = true;

               if (calculateBalance((BSTNode<T>) node.getRight()) > 0) {
                  notZZ = false;
                  this.rightRotation((BSTNode<T>) node.getRight());
               }

               this.leftRotation(node);

               if (notZZ) {
                  this.RRcounter++;
               } else {
                  this.RLcounter++;
               }
            }
         }
      }
   }

   @Override
   public void fillWithoutRebalance(T[] array) {
      if (array.length > 0) {
         this.sort(array);
         double div = (array.length - 1) / 2;
         this.fillingWithoutBalance(array, div);
      }

   }

   private void fillingWithoutBalance(T[] array, double div) {
 
      do {
    	  
         for (double i = div; i < array.length - 1; i += div) {

            if (i > (array.length - 1) / 2) {
               if (search(array[(int) Math.ceil(i)]).isEmpty()) {
                  insert(array[(int) Math.ceil(i)]);
               }
            } else {
               if (search(array[(int) i]).isEmpty()) {
                  insert(array[(int) Math.floor(i)]);

               }
            }
         }

         div = div / 2;

      } while (div > 1);

      for (int i = 0; i < array.length; i++) {
         if (search(array[i]).isEmpty()) {
            insert(array[i]);
         }
      }
   }
   
   private void sort(T[] array) {
		for (int i =  1; i < array.length; i++) {
			T aux = array[i];
			int j = i;

			while (j > 0 && (array[j - 1].compareTo(aux) > 0)) {
				T a = array[j - 1];
				array[j - 1] = array[j];
				array[j] = a;
				j--;
			}

		}
	}
}
