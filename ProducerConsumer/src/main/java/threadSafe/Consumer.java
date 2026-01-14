package threadSafe;

public class Consumer implements  Runnable {
    private final int periodo;
    private final Buffer<String> buffer;

    public Consumer(Buffer<String> buffer, int periodo) {
        this.buffer = buffer;
        this.periodo = periodo;
    }


    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(periodo);

                System.out.println(Thread.currentThread().getName() + " ha prelevato " + this.buffer.remove());
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
