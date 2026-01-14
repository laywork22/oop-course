import threadSafe.Buffer;
import threadSafe.Consumer;
import threadSafe.Producer;

public class TestThreadSafe {
    public static void main(String[] args) throws InterruptedException {
        Buffer<String> buffer = new Buffer<>(3);

        Thread p1 = new Thread(new Producer(buffer, 2000));

        Thread c1 = new Thread(new Consumer(buffer, 2000));

        Thread c2 = new Thread(new Consumer(buffer, 1500));


        p1.setName("Producer 1");

        c1.setName("Consumer 1");
        c2.setName("Consumer 2");

        p1.start();

        c1.start();
        c2.start();

        Thread.sleep(20000);

        p1.interrupt();

        c1.interrupt();
        c2.interrupt();
    }
}
