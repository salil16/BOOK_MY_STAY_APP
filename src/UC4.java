/**
 * UC4 - Guest can view available rooms without modifying system state.
 * Demonstrates read-only search functionality using proper OOP design.
 *
 * @author Salil
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

// ======================= MAIN CLASS =======================
public class UC4 {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (Polymorphism)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Guest views available rooms
        searchService.searchAvailableRooms(rooms);

        System.out.println("Search completed. No changes made to inventory.");
    }
}

// ======================= ABSTRACT CLASS =======================
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
        System.out.println("Room Type: " + getRoomType());
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

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 90);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 200);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

// ======================= INVENTORY CLASS =======================
class Room_Inventory {

    private Map<String, Integer> inventory;

    public Room_Inventory() {
        inventory = new HashMap<>();

        // Initialize availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void displayInventory() {
        System.out.println("=== Room Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// ======================= SEARCH SERVICE =======================
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {

        System.out.println("=== Available Rooms ===\n");

        for (Room room : rooms) {

            String type = room.getRoomType();
            int available = inventory.getAvailability(type);

            // Show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}