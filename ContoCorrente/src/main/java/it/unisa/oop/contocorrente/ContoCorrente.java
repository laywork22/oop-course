package it.unisa.oop.contocorrente;

public class ContoCorrente {
    private double saldo;

    public ContoCorrente(double saldo)  {
        this.saldo = saldo;
    }

    public synchronized double getSaldo() {
        return saldo;
    }

    public void versa(double importo) {
        if (importo <= 0) {
            throw new RuntimeException("Non è possibile versare: " + importo);
        }
        synchronized (this){
            saldo += importo;
        }
    }

    public synchronized void preleva(double importo) {
        if (importo <= 0 || importo > saldo) {
            throw new RuntimeException("Non è possibile prelevare: " + importo);
        }

        saldo -= importo;
    }

}
