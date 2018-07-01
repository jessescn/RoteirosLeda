package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;
	protected int size;

	public SingleLinkedListImpl() {
		this.head = null;
		this.size = 0;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> aux = this.head;
		while (aux.getNext() != null && !aux.getData().equals(element)) {
			aux = aux.getNext();
		}
		if (aux.getData().equals(element)) {
			return element;
		} else {
			return null;
		}
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> auxHead = this.head;
		if (this.head == null) {
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<>(element, this.head);
			newHead.setNext(this.head);
			this.head = newHead;
			size++;
		} else {
			while (auxHead.getNext() != null) {
				auxHead = auxHead.getNext();
			}
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<>(element, auxHead.getNext());
			auxHead.setNext(newHead);
			size++;
		}

	}

	@Override
	public void remove(T element) {
		if (this.head != null) {
			if (this.head.getData().equals(element)) {
				this.head = this.head.getNext();
				size--;
			} else {
				SingleLinkedListNode<T> aux = this.head;
				SingleLinkedListNode<T> prev = aux;
				while (aux.getNext() != null && !aux.getData().equals(element)) {
					prev = aux;
					aux = aux.getNext();
				}
				if (aux.getData().equals(element)) {
					prev.setNext(aux.getNext());
					size--;
				}
			}
		}

	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Comparable[size()];
		int index = 0;
		SingleLinkedListNode<T> aux = this.head;
		while (aux != null) {
			array[index] = aux.getData();
			aux = aux.getNext();
			index++;
		}
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
