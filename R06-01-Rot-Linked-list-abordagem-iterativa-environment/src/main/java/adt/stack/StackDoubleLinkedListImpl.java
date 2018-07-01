package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

   protected DoubleLinkedList<T> top;
   protected int size;

   public StackDoubleLinkedListImpl(int size) {
      this.size = size;
      this.top = new DoubleLinkedListImpl<T>();
   }

   @Override
   public void push(T element) throws StackOverflowException {
      if (!isFull()) {
         this.top.insert(element);
      } else {
         throw new StackOverflowException();
      }
   }

   @Override
   public T pop() throws StackUnderflowException {
      if (!isEmpty()) {
         T last = ((DoubleLinkedListImpl<T>) this.top).getLast().getData();
         this.top.removeLast();
         return last;
      } else {
         throw new StackUnderflowException();
      }
   }

   @Override
   public T top() {
      if (!isEmpty()) {
         T topo = ((DoubleLinkedListImpl<T>) this.top).getLast().getData();
         return topo;
      } else {
         return null;
      }
   }

   @Override
   public boolean isEmpty() {
      return this.top.isEmpty();
   }

   @Override
   public boolean isFull() {
      return this.size == this.top.size();
   }

}
