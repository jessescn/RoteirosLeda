package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;
import adt.linkedList.SingleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if(!isFull()) {
			this.list.insert(element);
		}else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if(!isEmpty()) {
			T value = ((SingleLinkedListImpl<T>) this.list).getHead().getData();
			this.list.removeFirst();
			return value;
		}else {
			throw new QueueUnderflowException();
		}
	}

	@Override
	public T head() {
		return ((SingleLinkedListImpl<T>) this.list).getHead().getData();
	}

	@Override
	public boolean isEmpty() {
		return this.list.size() == 0;
	}

	@Override
	public boolean isFull() {
		return this.size == this.list.size();
	}

}
