/**
 * UC8 - Booking History & Reporting System
 * Demonstrates historical tracking of confirmed bookings
 * without external storage (in-memory persistence).
 *
 * @author Salil
 * @version 1.0
 */

import java.util.*;

// ======================= MAIN CLASS =======================
public class UC8 {

    public static void main(String[] args) {

        // Booking history (persistence layer)
        BookingHistory history = new BookingHistory();

        // Report service (read-only analytics)
        BookingReportService reportService = new BookingReportService(history);

        // Simulated confirmed bookings
        history.addBooking(new Reservation("RES1001", "Alice", "Single Room"));
        history.addBooking(new Reservation("RES1002", "Bob", "Double Room"));
        history.addBooking(new Reservation("RES1003", "Charlie", "Suite Room"));
        history.addBooking(new Reservation("RES1004", "David", "Single Room"));

        // Admin requests full history
        reportService.showAllBookings();

        // Admin requests summary report
        reportService.generateReport();
    }
}

// ======================= RESERVATION =======================
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ======================= BOOKING HISTORY =======================
class BookingHistory {

    // Ordered storage (FIFO history)
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addBooking(Reservation r) {
        history.add(r);
        System.out.println("Stored in history: " + r.getReservationId());
    }

    // Get all bookings (read-only access)
    public List<Reservation> getAllBookings() {
        return history;
    }
}

// ======================= REPORT SERVICE =======================
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Show full booking history
    public void showAllBookings() {

        System.out.println("\n=== BOOKING HISTORY ===");

        for (Reservation r : history.getAllBookings()) {
            System.out.println(r.getReservationId()
                    + " | " + r.getGuestName()
                    + " | " + r.getRoomType());
        }
    }

    // Generate summary report
    public void generateReport() {

        System.out.println("\n=== SUMMARY REPORT ===");

        List<Reservation> bookings = history.getAllBookings();

        System.out.println("Total Bookings: " + bookings.size());

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : bookings) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\nRoom Type Distribution:");
        for (String key : roomCount.keySet()) {
            System.out.println(key + " -> " + roomCount.get(key));
        }
    }
}