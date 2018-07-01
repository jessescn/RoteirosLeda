package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
	}

	@Override
	public T head() {
		if(!isEmpty()) {
			return this.array[0];
		}else {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.tail == -1;
	}

	@Override
	public boolean isFull() {
		return this.tail == array.length - 1;
	}

	private void shiftLeft() {
		for (int i = 0; i < this.tail; i++) {
			array[i] = array[i + 1];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if(!isFull() && element != null) {
			this.tail++;
			this.array[tail] = element;
		}else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if(!isEmpty()) {
			T valor = this.array[0];
			this.tail--;
			shiftLeft();
			return valor;
		}else {
			throw new QueueUnderflowException();
		}
	}

}
