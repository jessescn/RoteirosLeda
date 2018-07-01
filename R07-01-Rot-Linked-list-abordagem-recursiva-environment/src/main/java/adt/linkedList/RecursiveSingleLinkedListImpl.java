package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return isNil();
	}
	
	public boolean isNil() {
		return this.data == null;
	}

	@Override
	public int size() {
		if (!isEmpty()) {
			return 1 + this.next.size();
		} else {
			return 0;
		}
	}

	@Override
	public T search(T element) {
		if (!isEmpty()) {
			if(this.next.isNil()) {
				if(this.data == element) {
					return element;
				}else {
					return null;
				}
			}else {
				if(this.data == element) {
					return element;
				}else {
					return this.next.search(element);
				}
			}
		}else {
			return null;
		}
			
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			this.data = element;
			this.next = new RecursiveSingleLinkedListImpl<>();
		} else {
			if (this.next.isNil()) {
				RecursiveSingleLinkedListImpl<T> aux = new RecursiveSingleLinkedListImpl<T>(element, new RecursiveSingleLinkedListImpl<>());
				this.next = aux;
			} else {
				this.next.insert(element);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (search(element) != null) {
			if (!this.next.isNil()) {
				if (this.next.getData() == element) {
					this.next = this.next.getNext();
				} else {
					this.next.remove(element);
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Comparable[this.size()];
		return toArray(0, array);

	}

	private T[] toArray(int currentIndex, T[] array) {
		if (!isNil()) {
			array[currentIndex] = this.data;
			this.next.toArray(currentIndex + 1,array);
		}
		return array;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
