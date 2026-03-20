/**
 * UC5 - Booking Request Queue
 * Demonstrates fair handling of booking requests using FIFO Queue.
 * No inventory updates are performed at this stage.
 *
 * @author Salil
 * @version 1.0
 */

import java.util.LinkedList;
import java.util.Queue;

// ======================= MAIN CLASS =======================
public class UC5 {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Guests submitting booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("David", "Single Room"));

        // Display queued requests
        requestQueue.showQueue();

        System.out.println("\nProcessing order (FIFO):");

        // Process requests in arrival order
        requestQueue.processRequests();

        System.out.println("\nAll booking requests processed.");
    }
}

// ======================= RESERVATION CLASS =======================
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ======================= QUEUE MANAGER =======================
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue (FIFO order preserved automatically)
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added: " +
                reservation.getGuestName() + " -> " +
                reservation.getRoomType());
    }

    // Show current queue
    public void showQueue() {
        System.out.println("\n=== Booking Queue ===");

        for (Reservation r : queue) {
            System.out.println(r.getGuestName() + " requested " + r.getRoomType());
        }
    }

    // Process requests in FIFO order
    public void processRequests() {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll(); // removes first element

            System.out.println("Processing: " +
                    r.getGuestName() + " booking for " +
                    r.getRoomType());
        }
    }
}