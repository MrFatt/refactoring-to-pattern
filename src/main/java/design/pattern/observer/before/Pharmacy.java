package design.pattern.observer.before;

public class Pharmacy {
    private GrandPa grandPa;

    public Pharmacy(GrandPa grandPa) {
        this.grandPa = grandPa;
    }

    public void sendSoap(){
        grandPa.shopping();
    }
}
