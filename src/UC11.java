/**
 * UC11 - Concurrent Booking System with Thread Safety
 * Demonstrates race conditions and synchronization for correctness.
 *
 * @author Your Name
 * @version 1.0
 */

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

// ======================= MAIN CLASS =======================
public class UC11 {

    public static void main(String[] args) throws InterruptedException {

        // Shared booking system
        ConcurrentBookingSystem system = new ConcurrentBookingSystem();

        // Add booking requests (shared queue)
        system.addRequest(new Reservation("Alice", "Single Room"));
        system.addRequest(new Reservation("Bob", "Single Room"));
        system.addRequest(new Reservation("Charlie", "Double Room"));
        system.addRequest(new Reservation("David", "Single Room"));
        system.addRequest(new Reservation("Eve", "Double Room"));

        // Create multiple threads (simulating concurrent guests)
        Thread t1 = new Thread(new BookingWorker(system), "Guest-1");
        Thread t2 = new Thread(new BookingWorker(system), "Guest-2");
        Thread t3 = new Thread(new BookingWorker(system), "Guest-3");

        // Start threads simultaneously
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        t1.join();
        t2.join();
        t3.join();

        System.out.println("\n=== FINAL INVENTORY STATE ===");
        system.showInventory();
    }
}

// ======================= RESERVATION =======================
class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ======================= THREAD WORKER =======================
class BookingWorker implements Runnable {

    private ConcurrentBookingSystem system;

    public BookingWorker(ConcurrentBookingSystem system) {
        this.system = system;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r = system.getNextRequest();

            if (r == null) break;

            system.processBooking(r);
        }
    }
}

// ======================= CORE SYSTEM =======================
class ConcurrentBookingSystem {

    // Shared queue (thread-safe)
    private Queue<Reservation> queue = new ConcurrentLinkedQueue<>();

    // Shared inventory
    private Map<String, Integer> inventory = new HashMap<>();

    private int counter = 100;

    public ConcurrentBookingSystem() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // Add request
    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added: " + r.guestName +
                " -> " + r.roomType);
    }

    // Get next request safely
    public Reservation getNextRequest() {
        return queue.poll();
    }

    // ================= CRITICAL SECTION =================
    public synchronized void processBooking(Reservation r) {

        System.out.println(Thread.currentThread().getName()
                + " processing " + r.guestName);

        if (!inventory.containsKey(r.roomType)) {
            System.out.println("INVALID ROOM TYPE");
            return;
        }

        int available = inventory.get(r.roomType);

        if (available <= 0) {
            System.out.println("REJECTED ❌ No availability for "
                    + r.roomType);
            return;
        }

        // Simulate allocation delay (race condition visibility)
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Critical update (protected by synchronized)
        inventory.put(r.roomType, available - 1);

        String roomId = r.roomType.substring(0, 2).toUpperCase()
                + counter++;

        System.out.println("CONFIRMED ✔ " + r.guestName
                + " -> " + roomId);
    }

    // Show final inventory
    public void showInventory() {
        for (String k : inventory.keySet()) {
            System.out.println(k + " -> " + inventory.get(k));
        }
    }
}