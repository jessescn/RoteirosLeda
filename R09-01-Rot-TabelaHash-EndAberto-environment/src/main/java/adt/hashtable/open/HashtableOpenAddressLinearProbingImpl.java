package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
      super(size);
      hashFunction = new HashFunctionLinearProbing<T>(size, method);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {
      if (element != null) {
         if (!isFull()) {
            HashFunctionLinearProbing<T> h = (HashFunctionLinearProbing<T>) this.hashFunction;
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
         HashFunctionLinearProbing<T> h = (HashFunctionLinearProbing<T>) this.hashFunction;
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
      HashFunctionLinearProbing<T> h = (HashFunctionLinearProbing<T>) this.hashFunction;
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
         HashFunctionLinearProbing<T> h = (HashFunctionLinearProbing<T>) this.hashFunction;
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
