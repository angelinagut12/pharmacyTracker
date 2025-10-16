package app;

public class Product {
    public int id;
    public String name;
    public String sku;
    public int quantity;
    public double price;
    public int minStock;

    public Product(int id, String name, String sku, int quantity, double price, int minStock) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
        this.minStock = minStock;
    }

    @Override
    public String toString() {
        return String.format("%-4d | %-20s | %-10s | qty=%-5d | $%-7.2f | min=%d",
            id, name, sku, quantity, price, minStock);
    }
}
