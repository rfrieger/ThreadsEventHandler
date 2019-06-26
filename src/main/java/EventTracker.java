import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    public Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        if(tracker.containsKey(message)){
            Integer integer =  tracker.get(message);
            tracker.replace(message, integer+1);
        }
        else {
            tracker.put(message, 1);
        }
    }

    synchronized public Boolean has(String message) {
        if(tracker != null && tracker.get(message) > 0) {
            return true;
        }
        return false;
    }

    synchronized public void handle(String message, EventHandler e) {

        e.handle();
        if(this.has(message)) {
            tracker.replace(message, tracker.get(message) - 1);
        }
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}
