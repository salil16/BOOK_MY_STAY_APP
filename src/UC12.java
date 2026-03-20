/**
 * UC12 - Persistence and Recovery System
 * Demonstrates serialization, deserialization,
 * and state recovery across application restarts.
 *
 * @author Your Name
 * @version 1.0
 */

import java.io.*;
import java.util.*;

// ======================= MAIN CLASS =======================
public class UC12 {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Try to load previous state
        SystemState state = persistence.loadState();

        if (state == null) {
            System.out.println("No previous state found. Creating new system...");
            state = new SystemState();
        }

        // Simulate operations
        state.bookings.add("RES1001");
        state.bookings.add("RES1002");

        state.inventory.put("Single Room",
                state.inventory.getOrDefault("Single Room", 2) - 1);

        System.out.println("\n=== CURRENT STATE BEFORE SAVE ===");
        state.showState();

        // Save state before shutdown
        persistence.saveState(state);

        System.out.println("\nSystem shutdown complete. State saved.");
    }
}

// ======================= SYSTEM STATE (SERIALIZABLE) =======================
class SystemState implements Serializable {

    Map<String, Integer> inventory = new HashMap<>();
    List<String> bookings = new ArrayList<>();

    public SystemState() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public void showState() {

        System.out.println("\n--- INVENTORY ---");
        for (String k : inventory.keySet()) {
            System.out.println(k + " -> " + inventory.get(k));
        }

        System.out.println("\n--- BOOKINGS ---");
        for (String b : bookings) {
            System.out.println(b);
        }
    }
}

// ======================= PERSISTENCE SERVICE =======================
class PersistenceService {

    private final String FILE_NAME = "hotel_state.dat";

    // Save state to file (serialization)
    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("\nState successfully saved to file.");

        } catch (IOException e) {
            System.out.println("ERROR saving state: " + e.getMessage());
        }
    }

    // Load state from file (deserialization)
    public SystemState loadState() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("Persistence file not found.");
            return null;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {

            System.out.println("Loading previous system state...");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("ERROR loading state. Starting fresh system.");
            return null;
        }
    }
}