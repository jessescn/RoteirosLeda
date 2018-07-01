package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		this.last = null;
		this.head = null;

	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<>();
			newHead.setData(element);
			newHead.setNext(this.head);
			newHead.setPrevious(null);
			this.head = newHead;
			size++;
		}

	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			DoubleLinkedListNode<T> newHead = (DoubleLinkedListNode<T>) this.head.getNext();
			newHead.setPrevious(null);
			this.head = newHead;
			size--;
		}

	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			DoubleLinkedListNode<T> newLast = this.last.getPrevious();
			this.last = newLast;
			newLast.setNext(null);
			this.size--;
		}
	}

	@Override
	public void insert(T element) {
		DoubleLinkedListNode<T> newNote = new DoubleLinkedListNode<>();
		newNote.setData(element);

		if (this.head == null) {
			newNote.setNext(this.head);
			this.head = newNote;
			this.size++;
			
		} else {
			DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.head;
			while (aux.getNext() != null) {
				aux = (DoubleLinkedListNode<T>) aux.getNext();
			}
			newNote.setPrevious(aux);
			aux.setNext(newNote);
			this.last = newNote;
			this.size++;
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null) {
			if (this.head.getData().equals(element)) {
				this.head = this.head.getNext();
				this.size--;

			} else if (this.last.getData().equals(element)) {
				this.last = this.last.getPrevious();
				this.last.setNext(null);
				this.size--;
			} else {
				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.head;

				while (aux != null && !aux.getData().equals(element)) {
					aux = (DoubleLinkedListNode<T>) aux.getNext();
				}

				if (aux != null) {
					DoubleLinkedListNode<T> prox = (DoubleLinkedListNode<T>) aux.getNext();
					DoubleLinkedListNode<T> ant = aux.getPrevious();
					ant.setNext(prox);
					prox.setPrevious(ant);
					this.size--;
				}

			}
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
