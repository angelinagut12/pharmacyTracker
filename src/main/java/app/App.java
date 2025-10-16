// Author: Angelina Gutierrez
// Project: Pharmacy Inventory CLI (Java 17)
// Description: Simple inventory tracker that stores product info in a CSV file.
package app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        InventoryService service = new InventoryService(new ProductFileStore("data/products.csv"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n Welcome to Angelina's Pharmacy Tracker");
            System.out.println("1) List products");
            System.out.println("2) Add product");
            System.out.println("3) Update quantity");
            System.out.println("4) Delete product");
            System.out.println("5) Low-stock report");
            System.out.println("6) Total inventory value");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> service.listProducts();
                    case "2" -> service.addProduct(sc);
                    case "3" -> service.updateQuantity(sc);
                    case "4" -> service.deleteProduct(sc);
                    case "5" -> service.lowStockReport(sc);
                    case "6" -> service.totalValue();    
                    case "0" -> { System.out.println("Bye!"); return; }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
