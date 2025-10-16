package app;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryService {
    private final ProductFileStore store;

    public InventoryService(ProductFileStore store) {
        this.store = store;
    }

    public void listProducts() throws Exception {
        var list = store.load();
        if (list.isEmpty()) System.out.println("No products yet. Add some!");
        else list.forEach(System.out::println);
    }

    public void addProduct(Scanner sc) throws Exception {
        var list = store.load();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("SKU (unique): ");
        String sku = sc.nextLine().trim();
        if (list.stream().anyMatch(p -> p.sku.trim().equalsIgnoreCase(sku))) {
            throw new RuntimeException("SKU already exists.");
        }
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Min stock: ");
        int min = Integer.parseInt(sc.nextLine().trim());

        int id = store.nextId(list);
        list.add(new Product(id, name, sku, qty, price, min));
        store.save(list);
        System.out.println("Added.");
    }

    public void updateQuantity(Scanner sc) throws Exception {
        var list = store.load();
        System.out.print("SKU: ");
        String sku = sc.nextLine().trim();
        var p = list.stream().filter(x -> x.sku.equalsIgnoreCase(sku)).findFirst()
            .orElseThrow(() -> new RuntimeException("SKU not found."));
        System.out.print("New quantity: ");
        int qty = Integer.parseInt(sc.nextLine().trim());
        p.quantity = qty;
        store.save(list);
        System.out.println("Updated.");
    }

    public void deleteProduct(Scanner sc) throws Exception {
        var list = store.load();
        System.out.print("SKU to delete: ");
        String sku = sc.nextLine().trim();
        boolean changed = list.removeIf(p -> p.sku.equalsIgnoreCase(sku));
        if (!changed) throw new RuntimeException("SKU not found.");
        store.save(list);
        System.out.println("Deleted.");
    }

    public void lowStockReport(Scanner sc) throws Exception {
        var list = store.load();
        System.out.print("Threshold (enter for default 0): ");
        String t = sc.nextLine().trim();
        int threshold = t.isEmpty() ? 0 : Integer.parseInt(t);
        var lows = list.stream()
            .filter(p -> p.quantity <= p.minStock || p.quantity <= threshold)
            .sorted(Comparator.comparingInt(p -> p.quantity))
            .collect(Collectors.toList());
        if (lows.isEmpty()) System.out.println("No low-stock items.");
        else {
            System.out.println("\n--- Low Stock ---");
            lows.forEach(System.out::println);
        }
    }

    public void totalValue() throws Exception {
        var list = store.load();
        double sum = 0.0;
        for (var p : list) {
            sum += p.quantity * p.price;
        }

        // format as currency
        java.text.NumberFormat nf = java.text.NumberFormat.getCurrencyInstance();
        System.out.println("\n--- Total Inventory Value ---");
        System.out.println(nf.format(sum));
    }

}
