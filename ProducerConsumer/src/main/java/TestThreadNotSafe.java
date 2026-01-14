import nothreadsafe.Buffer;
import nothreadsafe.Consumer;
import nothreadsafe.Producer;

public class TestThreadNotSafe {
    public static void main(String[] args) {
        Buffer<String> buffer = new Buffer<>(3);

        Thread t1 = new Thread(new Producer(2000, buffer));
        Thread t2 = new Thread(new Consumer(buffer, 1000));
        Thread t3 = new Thread(new Consumer(buffer, 1000));

        t1.setName("Producer 1");
        t2.setName("Consumer 1");
        t3.setName("Consumer 2");

        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {
        }
    }
}
