package nothreadsafe;

import java.util.Random;
import java.util.logging.Logger;

public class Producer implements Runnable{
    private final Buffer<String> buffer;
    private final int periodo;

    public Producer(int periodo, Buffer<String> buffer) {
        this.periodo = periodo;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random n = new Random(12500);

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(periodo);

                String msg = "Info" + n.nextInt(10);

                //bisogna gestire sincronizzazione e attesa attiva del thread
                synchronized(buffer) {

                    while(buffer.isFull()) {
                        buffer.wait();
                    }

                    buffer.addElement(msg);

                    System.out.println(Thread.currentThread().getName() + " ha inserito: " + msg);

                    buffer.notifyAll();
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
