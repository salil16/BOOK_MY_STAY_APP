/**
 * UC4 - Guest Room Search (Fixed Version)
 * Read-only search using Inventory + Room domain model.
 *
 * @author Salil
 * @version 1.0
 */

import java.util.*;

// ======================= MAIN CLASS =======================
public class UC4 {

    public static void main(String[] args) {

        // Create inventory (STATE HOLDER)
        RoomInventory inventory = new RoomInventory();

        // Room objects (DOMAIN MODEL)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service (READ-ONLY)
        SearchService searchService = new SearchService(inventory);

        // Guest search
        searchService.searchAvailableRooms(rooms);

        System.out.println("\nSearch completed. No changes made to inventory.");
    }
}

// ======================= ROOM (ABSTRACT CLASS) =======================
abstract class Room {

    private int beds;
    private double size;
    private double price;

    public Room(int beds, double size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();

    public void displayDetails() {
        System.out.println("\nRoom Type: " + getRoomType());
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }
}

// ======================= CHILD CLASSES =======================
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 50);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 90);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 600, 200);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

// ======================= INVENTORY CLASS =======================
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// ======================= SEARCH SERVICE =======================
class SearchService {

    private RoomInventory inventory;

    // FIX: inventory must be passed correctly here
    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {

        System.out.println("=== Available Rooms ===");

        for (Room room : rooms) {

            String type = room.getRoomType();
            int available = inventory.getAvailability(type);

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
            }
        }
    }
}