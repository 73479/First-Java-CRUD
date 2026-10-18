import java.util.*;

public class Main {
    static class product {
        int id;
        String name;
        double price;

        product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("[ID: %d] %-15s | $%.2f", id, name, price);
        }
    }

    private static final Map<Integer, product> database = new LinkedHashMap<>();
    private static int nextId = 1;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data
        database.put(nextId, new product(nextId++, "Keyboard", 49.99));
        database.put(nextId, new product(nextId++, "Mouse", 25.00));

        while (true) {
            System.out.println("\n=== SIMPLE CRUD MENU ===");
            System.out.println("1. View All products");
            System.out.println("2. Add product");
            System.out.println("3. Update product");
            System.out.println("4. Delete product");
            System.out.println("5. Exit");
            System.out.print("Choose (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewAll();
                case "2" -> addproduct();
                case "3" -> updateproduct();
                case "4" -> deleteproduct();
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
            System.out.println("No products in database.");
            return;
        }
        System.out.println("\n--- products List ---");
        database.values().forEach(System.out::println);
    }

    private static void addproduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        product product = new product(nextId, name, price);
        database.put(nextId++, product);
        System.out.println("Added: " + product);
    }

    private static void updateproduct() {
        System.out.print("Enter product ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        product product = database.get(id);

        if (product == null) {
            System.out.println("product not found!");
            return;
        }

        System.out.print("Enter new name: ");
        product.name = scanner.nextLine().trim();
        System.out.print("Enter new price: ");
        product.price = Double.parseDouble(scanner.nextLine().trim());

        System.out.println("Updated: " + product);
    }

    private static void deleteproduct() {
        System.out.print("Enter product ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        product removed = database.remove(id);

        if (removed != null) {
            System.out.println("Deleted ID " + id);
        } else {
            System.out.println("product not found!");
        }
    }
}