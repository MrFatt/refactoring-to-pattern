package design.pattern.observer.after;

import java.util.ArrayList;
import java.util.List;

public abstract class Shop {
    private List<OnSalesListener> listeners = new ArrayList<>();

    public void addListeners (OnSalesListener listener) {
        listeners.add(listener);
    }

    public void sendStuff(){
        listeners.stream().forEach(OnSalesListener::shopping);
    }
}
