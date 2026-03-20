/**
 * RoomInventory manages the availability of different room types
 * using a centralized HashMap.
 *
 * It acts as a single source of truth for room availability.
 *
 * @author Your Name
 * @version 1.0
 */
import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private Map<String, Integer> inventory;

    /**
     * Constructor initializes room availability.
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /**
     * Get availability for a specific room type.
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability for a room type.
     */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Display full inventory.
     */
    public void displayInventory() {
        System.out.println("=== Room Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
/**
 * UC3 - Demonstrates centralized inventory management using HashMap.
 *
 * @author Your Name
 * @version 1.0
 */
public class UC3 {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("\nAvailable Single Rooms: " +
                inventory.getAvailability("Single Room"));

        // Update availability (simulate booking)
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\nAfter booking one Single Room:");

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}