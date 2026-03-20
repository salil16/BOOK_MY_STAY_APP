import java.util.*;

public class UC6 {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));

        BookingService service = new BookingService(inventory);
        service.processBookings(queue);

        System.out.println("\nFinal Inventory:");
        inventory.showInventory();
    }
}

// ================= RESERVATION =================
class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ================= QUEUE =================
class BookingQueue {

    Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Added: " + r.guestName);
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }

    public Reservation nextRequest() {
        return queue.poll();
    }
}

// ================= INVENTORY =================
class InventoryService {

    Map<String, Integer> inventory = new HashMap<>();
    int counter = 100;

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public String allocate(String type) {

        if (!isAvailable(type)) return null;

        inventory.put(type, inventory.get(type) - 1);

        return type.substring(0, 2).toUpperCase() + counter++;
    }

    public void showInventory() {
        for (String k : inventory.keySet()) {
            System.out.println(k + " -> " + inventory.get(k));
        }
    }
}

// ================= SERVICE =================
class BookingService {

    InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBookings(BookingQueue queue) {

        while (queue.hasRequests()) {

            Reservation r = queue.nextRequest();

            System.out.println("\nProcessing: " + r.guestName);

            if (inventory.isAvailable(r.roomType)) {
                String id = inventory.allocate(r.roomType);
                System.out.println("CONFIRMED ✔ " + id);
            } else {
                System.out.println("REJECTED ✖");
            }
        }
    }
}