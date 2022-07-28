public class Main {
    public static final int BUYERS = 5;

    public static void main(String[] args) {
        final Dealer dealer = new Dealer();
        for (int i = 1; i <= BUYERS; i++) {
            new Thread(null, dealer::sellCar, "Buyer " + i).start();
        }
        new Thread(null, dealer::receivingCar, "Opel car manufacturer").start();
    }
}
