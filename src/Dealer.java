import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dealer {
    private static final int RECEIVING_TIME = 2000;
    private static final int SELL_TIME = 1000;
    private static final int CARS = 5;
    private final List<Car> cars = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void receivingCar() {
        for (int i = 0; i < CARS; i++) {
            try {
                Thread.sleep(RECEIVING_TIME);
                cars.add(new Car());
                System.out.println(Thread.currentThread().getName() + " produced one car.");
                lock.lock();
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " went to a car dealership.");
            while (cars.isEmpty()) {
                System.out.println("There are no cars!");
                condition.await();
            }
            Thread.sleep(SELL_TIME);
            System.out.println(Thread.currentThread().getName() + " left in a brand new Opel car.");
            cars.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
