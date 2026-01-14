package threadSafe;

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
        return buffer.size() == size;
    }

    public boolean isEmpty() {
        return buffer.size() == size;
    }

    public synchronized void add(E element) throws InterruptedException {
        while(isFull()) {
            wait();
        }

        this.buffer.add(element);

        //risveglio tutti i thread per avvisare dell'aggiunta di questo elemento al buffer
        notifyAll();
    }

    public synchronized E remove() throws InterruptedException {

        while(isEmpty()) {
            wait();
        }

        notifyAll();
        //i thread si risveglieranno ma quello attuale non cederà il controllo fino a dopo la rimozione

        return this.buffer.remove();
    }
}
