import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    static class Product {
        private final int id;
        private String name;
        private double price;

        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return String.format("[%d] %-20s | $%.2f", id, name, price);
        }
    }

    private static final List<Product> database = new ArrayList<>();
    private static int nextId = 1;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Seed default records
        create("Mechanical Keyboard", 89.99);
        create("Wireless Mouse", 34.50);

        while (true) {
            System.out.println("\n--- PRODUCT INVENTORY (CRUD) ---");
            System.out.println("1. View All (Read)");
            System.out.println("2. Add Product (Create)");
            System.out.println("3. Update Product (Update)");
            System.out.println("4. Delete Product (Delete)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> readAll();
                case "2" -> handleCreate();
                case "3" -> handleUpdate();
                case "4" -> handleDelete();
                case "5" -> {
                    System.out.println("Terminating session. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid selection. Try again.");
            }
        }
    }

    // CREATE
    private static void create(String name, double price) {
        database.add(new Product(nextId++, name, price));
    }

    private static void handleCreate() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter product price: ");
        double price = parseDoubleInput();

        create(name, price);
        System.out.println("Product added successfully.");
    }

    // READ
    private static void readAll() {
        if (database.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("\nCurrent Catalog:");
        database.forEach(System.out::println);
    }

    // UPDATE
    private static void handleUpdate() {
        System.out.print("Enter product ID to update: ");
        int id = parseIntInput();

        Optional<Product> target = findById(id);
        if (target.isEmpty()) {
            System.out.println("Product with ID " + id + " not found.");
            return;
        }

        Product product = target.get();
        System.out.print("Enter new name (leave blank to keep '" + product.getName() + "'): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            product.setName(newName);
        }

        System.out.print("Enter new price (or enter -1 to keep $" + product.getPrice() + "): ");
        double newPrice = parseDoubleInput();
        if (newPrice >= 0) {
            product.setPrice(newPrice);
        }

        System.out.println("Product updated successfully.");
    }

    // DELETE
    private static void handleDelete() {
        System.out.print("Enter product ID to delete: ");
        int id = parseIntInput();

        boolean removed = database.removeIf(p -> p.getId() == id);
        if (removed) {
            System.out.println("Product ID " + id + " deleted.");
        } else {
            System.out.println("Product ID " + id + " not found.");
        }
    }

    // HELPERS
    private static Optional<Product> findById(int id) {
        return database.stream().filter(p -> p.getId() == id).findFirst();
    }

    private static int parseIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double parseDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}