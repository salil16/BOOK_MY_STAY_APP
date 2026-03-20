/**
 * UC9 - Booking Validation & Error Handling
 * Demonstrates structured validation, custom exceptions,
 * and fail-fast system design.
 *
 * @author Your Name
 * @version 1.0
 */

import java.util.*;

// ======================= MAIN CLASS =======================
public class UC9 {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();

        BookingProcessor processor = new BookingProcessor(inventory);

        // Valid booking
        try {
            processor.bookRoom("Alice", "Single Room");
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }

        // Invalid room type
        try {
            processor.bookRoom("Bob", "King Room");
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }

        // Overbooking test
        try {
            processor.bookRoom("Charlie", "Single Room");
            processor.bookRoom("David", "Single Room"); // may fail if no stock
            processor.bookRoom("Eve", "Single Room");   // triggers error
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nSystem completed safely.");
    }
}

// ======================= CUSTOM EXCEPTION =======================
class BookingException extends Exception {

    public BookingException(String message) {
        super(message);
    }
}

// ======================= INVENTORY SERVICE =======================
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Validate room type exists
    public boolean isValidRoom(String roomType) {
        return inventory.containsKey(roomType);
    }

    // Check availability
    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    // Safe allocation (prevents negative inventory)
    public void allocate(String roomType) throws BookingException {

        if (!isValidRoom(roomType)) {
            throw new BookingException("INVALID ROOM TYPE: " + roomType);
        }

        if (!isAvailable(roomType)) {
            throw new BookingException("ROOM NOT AVAILABLE: " + roomType);
        }

        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("BOOKING CONFIRMED ✔ " + roomType);
    }

    public void showInventory() {
        System.out.println("\n=== INVENTORY STATE ===");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// ======================= BOOKING PROCESSOR =======================
class BookingProcessor {

    private InventoryService inventory;

    public BookingProcessor(InventoryService inventory) {
        this.inventory = inventory;
    }

    // Validate + process booking (FAIL FAST)
    public void bookRoom(String guestName, String roomType) throws BookingException {

        System.out.println("\nProcessing booking for: " + guestName);

        // Step 1: Validate input
        if (guestName == null || guestName.isEmpty()) {
            throw new BookingException("INVALID GUEST NAME");
        }

        if (roomType == null || roomType.isEmpty()) {
            throw new BookingException("INVALID ROOM TYPE INPUT");
        }

        // Step 2: Allocate room
        inventory.allocate(roomType);
    }
}