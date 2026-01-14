package nothreadsafe;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer<E> {
    private final Queue<E> buffer;
    private final int size;

    public Buffer(int size) {
        this.size = size;
        this.buffer = new LinkedList<>();
    }

    public boolean isFull() {
        return this.buffer.size() == size;
    }

    public boolean isEmpty() {
        return this.buffer.isEmpty();
    }

    public void addElement(E element) {
        this.buffer.add(element);
    }

    public E removeElement() {
        return this.buffer.remove();
    }
}
