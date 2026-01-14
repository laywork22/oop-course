package it.unisa.oop.contocorrente;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        ContoCorrente c = new ContoCorrente(1000);

        Checker check = new Checker(1000);

        Thread t1 = new Thread(new GuadagnaGuadagna(c, 3000));
        Thread t2 = new Thread(new SpendiSpendi(2000, c));

        check.add(t1);
        check.add(t2);

        Thread t3 = new Thread(check);

        t1.setName("GuadagnaGuadagna");
        t2.setName("SpendiSpendi");

        t3.start();
        t1.start();
        t2.start();


        Thread.sleep(10000);


        t1.interrupt();
        t2.interrupt();
        t3.interrupt();


        t1.join();
        t2.join();
        t3.join();

        System.out.println("Esecuzione terminata. Saldo finale: " + c.getSaldo());
    }
}