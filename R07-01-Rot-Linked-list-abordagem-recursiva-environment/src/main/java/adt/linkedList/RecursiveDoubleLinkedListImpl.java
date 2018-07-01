package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {
		this.next = (RecursiveDoubleLinkedListImpl<T>) this.next;
	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			this.data = element;
			this.next = new RecursiveDoubleLinkedListImpl<>();
		} else {
			if (this.next.isNil()) {
				RecursiveDoubleLinkedListImpl<T> aux = new RecursiveDoubleLinkedListImpl<T>(element, getNext(), this);
				this.next = aux;
			} else {
				this.next.insert(element);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (isEmpty()) {
			this.data = element;
			setNext(new RecursiveDoubleLinkedListImpl<>());
			((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious(this);
		} else {
			RecursiveDoubleLinkedListImpl<T> aux = new RecursiveDoubleLinkedListImpl<T>();
			aux.setData(this.data);
			aux.setNext(this.next);
			aux.setPrevious(this);
			this.setData(element);
			this.setNext(aux);

		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			if (!this.next.isNil()) {
				setData(this.next.getData());
				setNext(this.next.getNext());
				setPrevious(new RecursiveDoubleLinkedListImpl<>());
			} else {
				setData(null);
				setNext(null);
			}
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			if (this.next.isNil()) {
				if (this.getPrevious() != null) {
					getPrevious().setNext(this.next);
					((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious(getPrevious());
				} else {
					removeFirst();
				}
			} else {
				((RecursiveDoubleLinkedListImpl<T>) this.next).removeLast();

			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
