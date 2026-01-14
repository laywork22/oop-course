package nothreadsafe;

import java.util.Queue;

public class Consumer implements Runnable{
    private final Buffer<String> buffer;
    private final int periodo;

    public Consumer(Buffer<String> buffer, int periodo) {
        this.buffer = buffer;
        this.periodo = periodo;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(periodo);

                synchronized(buffer) {
                    while(buffer.isEmpty()) {
                        buffer.wait();
                    }

                    System.out.println(Thread.currentThread().getName() + " ha letto: " + buffer.removeElement());

                    buffer.notifyAll();
                }

            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
