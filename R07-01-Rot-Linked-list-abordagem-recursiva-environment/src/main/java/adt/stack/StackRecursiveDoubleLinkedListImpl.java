package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (!isFull()) {
			if (element != null) {
				this.top.insert(element);
			}
		} else {
			throw new StackOverflowException();
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(!isEmpty()) {
			T value = this.top.toArray()[top.size() - 1];
			this.top.removeLast();
			return value;
		}else {
			throw new StackUnderflowException();
		}
	}

	@Override
	public T top() {
		if(!isEmpty()) {
			T value = this.top.toArray()[top.size() - 1];
			return value;
		}else {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.top.size() == 0;
	}

	@Override
	public boolean isFull() {
		return this.top.size() == this.size;
	}

}
