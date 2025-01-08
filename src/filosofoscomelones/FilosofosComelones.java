package filosofoscomelones;

/**
 *
 * @author carlo
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Filosofo implements Runnable {
    private final int id;
    private final Lock tenedorIzquierdo;
    private final Lock tenedorDerecho;

    public Filosofo(int id, Lock tenedorIzquierdo, Lock tenedorDerecho) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                comer();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filosofo " + id + " está pensando...");
        Thread.sleep(500);
        Thread.sleep((int) (Math.random() * 1000));
    }

    private void comer() throws InterruptedException {
        if (id % 2 == 0) {
            tenedorIzquierdo.lock();
            System.out.println("Filosofo " + id + " toma su tenedor izquierdo...");
            Thread.sleep(500);

            tenedorDerecho.lock();
            System.out.println("Filosofo " + id + " toma su tenedor derecho...");
            Thread.sleep(500);
        } else {
            tenedorDerecho.lock();
            System.out.println("Filosofo " + id + " toma su tenedor derecho...");
            Thread.sleep(500);

            tenedorIzquierdo.lock();
            System.out.println("Filosofo " + id + " toma su tenedor izquierdo...");
            Thread.sleep(500);
        }

        System.out.println("Filosofo " + id + " está comiendo...");
        Thread.sleep(500);
        Thread.sleep((int) (Math.random() * 1000));

        tenedorIzquierdo.unlock();
        System.out.println("Filosofo " + id + " suelta su tenedor izquierdo...");
        Thread.sleep(500);

        tenedorDerecho.unlock();
        System.out.println("Filosofo " + id + " suelta su tenedor derecho...");
        Thread.sleep(500);
    }
}

public class FilosofosComelones {
    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;
        Lock[] tenedores = new Lock[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            tenedores[i] = new ReentrantLock();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_FILOSOFOS);
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            Lock tenedorIzquierdo = tenedores[i];
            Lock tenedorDerecho = tenedores[(i + 1) % NUM_FILOSOFOS];
            executorService.submit(new Filosofo(i + 1, tenedorIzquierdo, tenedorDerecho));
        }

    }
}
