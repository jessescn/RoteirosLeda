package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
	}

	@Override
	public T top() {
		if (isEmpty()) {
			return null;
		} else {
			return array[top];
		}
	}

	@Override
	public boolean isEmpty() {
		return top == -1;
	}

	@Override
	public boolean isFull() {
		return top == array.length - 1;
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (!isFull() && element != null) {
			this.top++;
			this.array[top] = element;
		} else {
			throw new StackOverflowException();

		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (!isEmpty()) {
			T valor = this.array[this.top];
			this.top--;
			return valor;
		} else {
			throw new StackUnderflowException();
		}
	}

}
