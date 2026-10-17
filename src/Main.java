import java.util.*;

public class Main {
    static class Item {
        int id;
        String name;
        double price;

        Item(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("[ID: %d] %-15s | $%.2f", id, name, price);
        }
    }

    private static final Map<Integer, Item> database = new LinkedHashMap<>();
    private static int nextId = 1;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data
        database.put(nextId, new Item(nextId++, "Keyboard", 49.99));
        database.put(nextId, new Item(nextId++, "Mouse", 25.00));

        while (true) {
            System.out.println("\n=== SIMPLE CRUD MENU ===");
            System.out.println("1. View All Items");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Choose (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewAll();
                case "2" -> addItem();
                case "3" -> updateItem();
                case "4" -> deleteItem();
                case "5" -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void viewAll() {
        if (database.isEmpty()) {
            System.out.println("No items in database.");
            return;
        }
        System.out.println("\n--- Items List ---");
        database.values().forEach(System.out::println);
    }

    private static void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Item item = new Item(nextId, name, price);
        database.put(nextId++, item);
        System.out.println("Added: " + item);
    }

    private static void updateItem() {
        System.out.print("Enter item ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Item item = database.get(id);

        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.print("Enter new name: ");
        item.name = scanner.nextLine().trim();
        System.out.print("Enter new price: ");
        item.price = Double.parseDouble(scanner.nextLine().trim());

        System.out.println("Updated: " + item);
    }

    private static void deleteItem() {
        System.out.print("Enter item ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Item removed = database.remove(id);

        if (removed != null) {
            System.out.println("Deleted ID " + id);
        } else {
            System.out.println("Item not found!");
        }
    }
}