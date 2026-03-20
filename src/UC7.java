/**
 * UC7 - Add-On Services for Hotel Booking
 * Demonstrates extensibility using Map + List without modifying core booking logic.
 *
 * @author Salil
 * @version 1.0
 */

import java.util.*;

// ======================= MAIN CLASS =======================
public class UC7 {

    public static void main(String[] args) {

        // Create add-on service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Create reservations (existing bookings)
        String reservation1 = "RES1001";
        String reservation2 = "RES1002";

        // Guest selects services for reservation 1
        serviceManager.addService(reservation1, new AddOnService("Breakfast", 10));
        serviceManager.addService(reservation1, new AddOnService("Airport Pickup", 25));

        // Guest selects services for reservation 2
        serviceManager.addService(reservation2, new AddOnService("Spa Access", 30));

        // Display services and cost
        serviceManager.displayServices();

        System.out.println("\nTotal Cost for " + reservation1 + ": "
                + serviceManager.calculateCost(reservation1));

        System.out.println("Total Cost for " + reservation2 + ": "
                + serviceManager.calculateCost(reservation2));
    }
}

// ======================= ADD-ON SERVICE =======================
class AddOnService {

    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// ======================= SERVICE MANAGER =======================
class AddOnServiceManager {

    // Map<ReservationID, List<Services>>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName()
                + " -> " + reservationId);
    }

    // Calculate total cost for a reservation
    public double calculateCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    // Display all services
    public void displayServices() {

        System.out.println("\n=== ADD-ON SERVICES ===");

        for (String reservationId : serviceMap.keySet()) {

            System.out.println("\nReservation: " + reservationId);

            for (AddOnService s : serviceMap.get(reservationId)) {
                System.out.println("- " + s.getName()
                        + " : $" + s.getCost());
            }
        }
    }
}