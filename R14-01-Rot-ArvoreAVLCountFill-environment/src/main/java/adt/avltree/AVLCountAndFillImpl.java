package adt.avltree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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

            if (balance > 0) {

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

            } else if (balance < 0) {
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
         T[] combined = combine(array);
         Arrays.sort(combined);
         this.fillingWithoutBalance(combined);
      }
   }

   private T[] combine(T[] array) {
      T[] arrayAux = Arrays.copyOf(array, array.length + this.size());

      if (!isEmpty()) {
         for (int i = array.length; i < arrayAux.length; i++) {
            arrayAux[i] = preOrder()[i - array.length];
         }
      }

      this.root = new BSTNode<T>();
      return arrayAux;
   }

   private void fillingWithoutBalance(T[] array) {

      Queue<Integer[]> fila = new LinkedList<>();

      Integer[] pair = new Integer[2];
      pair[0] = 0;
      pair[1] = array.length - 1;
      fila.add(pair);

      while(fila.size() > 0) {
    	  
         Integer[] internal = fila.peek();
         int index = (internal[0] + internal[1]) / 2;
         
         insert(array[index]);
         fila.remove();

         if (internal[0] <= internal[1]) {
            if (index - 1 >= internal[0]) {

               Integer[] aux = new Integer[2];
               aux[0] = internal[0];
               aux[1] = index - 1;
               fila.add(aux);

            }
            if (index + 1 <= internal[1]) {

               Integer[] aux = new Integer[2];
               aux[0] = index + 1;
               aux[1] = internal[1];
               fila.add(aux);
            }
         }

      }
   }

}
