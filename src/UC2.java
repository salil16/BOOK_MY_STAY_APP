/**
 * Abstract class representing a generic Room in the hotel.
 * Defines common properties and behavior for all room types.
 *
 * @author Salil
 * @version 1.0
 */
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

    // Abstract method (must be implemented by subclasses)
    public abstract String getRoomType();

    // Common method
    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }
}
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
/**
 * Main application to demonstrate room modeling and availability.
 *
 * @author Your Name
 * @version 1.0
 */
public class UC2 {

    public static void main(String[] args) {

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("=== Hotel Room Availability ===\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application terminated.");
    }
}