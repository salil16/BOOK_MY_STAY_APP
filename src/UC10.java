/**
 * UC10 - Safe Cancellation with State Rollback
 * Demonstrates controlled reversal of booking state using Stack.
 *
 * @author Your Name
 * @version 1.0
 */

import java.util.*;

// ======================= MAIN CLASS =======================
public class UC10 {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        // Confirm bookings
        service.confirmBooking("RES1001", "Alice", "Single Room");
        service.confirmBooking("RES1002", "Bob", "Single Room");
        service.confirmBooking("RES1003", "Charlie", "Double Room");

        System.out.println("\n--- CURRENT STATE ---");
        service.showState();

        // Cancellations
        System.out.println("\n--- CANCELLATIONS ---");
        service.cancelBooking("RES1002"); // valid
        service.cancelBooking("RES9999"); // invalid

        System.out.println("\n--- FINAL STATE ---");
        service.showState();
    }
}

// ======================= BOOKING MODEL =======================
class Booking {

    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean active;

    public Booking(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }
}

// ======================= CANCELLATION SERVICE =======================
class CancellationService {

    // Active bookings
    private Map<String, Booking> bookings = new HashMap<>();

    // Inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // LIFO rollback structure
    private Stack<String> releasedRoomIds = new Stack<>();

    private int counter = 100;

    public CancellationService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // ================= CONFIRM BOOKING =================
    public void confirmBooking(String resId, String guest, String roomType) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("BOOKING FAILED ❌ " + roomType);
            return;
        }

        String roomId = roomType.substring(0, 2).toUpperCase() + counter++;

        Booking b = new Booking(resId, guest, roomType, roomId);
        bookings.put(resId, b);

        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("BOOKED ✔ " + resId + " -> " + roomId);
    }

    // ================= CANCEL BOOKING =================
    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation: " + reservationId);

        // Validate existence
        if (!bookings.containsKey(reservationId)) {
            System.out.println("CANCEL FAILED ❌ Invalid reservation");
            return;
        }

        Booking b = bookings.get(reservationId);

        if (!b.active) {
            System.out.println("CANCEL FAILED ❌ Already cancelled");
            return;
        }

        // Step 1: Mark inactive
        b.active = false;

        // Step 2: Release room ID (LIFO rollback)
        releasedRoomIds.push(b.roomId);

        // Step 3: Restore inventory
        inventory.put(b.roomType,
                inventory.get(b.roomType) + 1);

        System.out.println("CANCELLED ✔ " + reservationId +
                " | Room Released: " + b.roomId);
    }

    // ================= SHOW SYSTEM STATE =================
    public void showState() {

        System.out.println("\n--- INVENTORY ---");
        for (String k : inventory.keySet()) {
            System.out.println(k + " -> " + inventory.get(k));
        }

        System.out.println("\n--- ACTIVE BOOKINGS ---");
        for (Booking b : bookings.values()) {
            System.out.println(b.reservationId +
                    " | " + b.guestName +
                    " | " + b.roomType +
                    " | " + b.roomId +
                    " | Active: " + b.active);
        }

        System.out.println("\n--- RELEASED ROOM IDS (STACK) ---");
        System.out.println(releasedRoomIds);
    }
}