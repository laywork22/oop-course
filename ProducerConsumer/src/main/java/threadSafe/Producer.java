package threadSafe;

import java.util.Random;

public class Producer implements Runnable {
    private final int periodo;
    private final Buffer<String> buffer;


    public Producer(Buffer<String> buffer, int periodo) {
        this.buffer = buffer;
        this.periodo = periodo;
    }


    @Override
    public void run() {
        Random n = new Random(12400);

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(periodo);

                String msg = "Info" + n.nextInt(10);

                buffer.add(msg);

                System.out.println(Thread.currentThread().getName() + " ha inserito: " + msg);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
