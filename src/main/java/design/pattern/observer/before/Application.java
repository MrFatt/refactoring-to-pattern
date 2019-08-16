package design.pattern.observer.before;

public class Application {
    public static void main(String[] args) {
        new SuperMarket(new GrandMa()).sendEggs();

        new Pharmacy(new GrandPa()).sendSoap();
    }
}
