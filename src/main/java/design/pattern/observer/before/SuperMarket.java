package design.pattern.observer.before;

public class SuperMarket {
    private GrandMa grandMa;

    public SuperMarket(GrandMa grandMa) {
        this.grandMa = grandMa;
    }

    public void sendEggs() {
        grandMa.shopping();
    }

}
