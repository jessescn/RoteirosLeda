package adt.queue;

public class CircularQueue<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int head;
	private int elements;

	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if(!isFull() && element != null) {
			this.tail++;
			this.tail = this.tail % array.length;
			array[tail] = element;
			this.elements ++;
		}else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if(!isEmpty()) {
			this.head = (this.head + 1) % array.length;
			this.elements--;
			T valor = array[this.head];
			return valor;	
		}else {
			throw new QueueUnderflowException();
		}
	}

	@Override
	public T head() {
		if(isEmpty()) {
			return null;
		}else {
			return this.array[this.head + 1];
		}
	}

	@Override
	public boolean isEmpty() {
		return this.elements == 0;
	}

	@Override
	public boolean isFull() {
		return this.elements == array.length;
	}

}
