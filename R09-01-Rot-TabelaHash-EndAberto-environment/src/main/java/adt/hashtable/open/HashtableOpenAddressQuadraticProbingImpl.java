package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
      super(size);
      hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {
      if (element != null) {
         if (!isFull()) {
            HashFunctionQuadraticProbing<T> h = (HashFunctionQuadraticProbing<T>) this.hashFunction;
            int i = 0;
            while (i < this.capacity()) {
               if (this.table[h.hash(element, i)] == this.deletedElement || this.table[h.hash(element, i)] == null) {
                  table[h.hash(element, i)] = element;
                  this.elements++;
                  break;
               } else {
                  if (!this.table[h.hash(element, i)].equals(element)) {
                     this.COLLISIONS++;
                  }else {
                	  this.table[h.hash(element, i)] = element;
                	  break;
                  }
               }
               i++;
            }

         } else {
            throw new HashtableOverflowException();
         }
      }
   }

   @Override
   public void remove(T element) {
      if (element != null && search(element) != null) {
         HashFunctionQuadraticProbing<T> h = (HashFunctionQuadraticProbing<T>) this.hashFunction;
         int i = 0;
         while (i < this.capacity()) {
            if (this.table[h.hash(element, i)].equals(element)) {
               this.table[h.hash(element, i)] = this.deletedElement;
               this.elements--;
               break;
            }
            i++;
         }
      }
   }

   @Override
   public T search(T element) {
      HashFunctionQuadraticProbing<T> h = (HashFunctionQuadraticProbing<T>) this.hashFunction;
      int i = 0;
      while (this.table[h.hash(element, i)] != null && i < this.capacity()) {
         if (this.table[h.hash(element, i)].equals(element)) {
            return element;
         }
         i++;
      }
      return null;
   }

   @Override
   public int indexOf(T element) {
      if (search(element) != null) {
         HashFunctionQuadraticProbing<T> h = (HashFunctionQuadraticProbing<T>) this.hashFunction;
         int i = 0;
         while (this.table[h.hash(element, i)] != null && i < this.capacity()) {
            if (this.table[h.hash(element, i)].equals(element)) {
               return h.hash(element, i);
            }
            i++;
         }

      }
      return -1;
   }
}
