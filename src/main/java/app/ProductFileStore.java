package app;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProductFileStore {
    private final Path file;

    public ProductFileStore(String path) {
        this.file = Paths.get(path);
    }

    public synchronized List<Product> load() throws IOException {
        if (!Files.exists(file)) return new ArrayList<>();
        List<String> lines = Files.readAllLines(file);
        List<Product> list = new ArrayList<>();
        boolean headerSkipped = false;
        for (String line : lines) {
            if (!headerSkipped) { headerSkipped = true; continue; }
            if (line.isBlank()) continue;
            String[] parts = splitCsv(line);
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String sku = parts[2];
            int qty = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4]);
            int min = Integer.parseInt(parts[5]);
            list.add(new Product(id, name, sku, qty, price, min));
        }
        return list;
    }

    public synchronized void save(List<Product> products) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("id,name,sku,quantity,price,min_stock");
        for (Product p : products) {
            lines.add(String.join(",", Arrays.asList(
                String.valueOf(p.id),
                escapeCsv(p.name),
                escapeCsv(p.sku),
                String.valueOf(p.quantity),
                String.valueOf(p.price),
                String.valueOf(p.minStock)
            )));
        }
        Files.createDirectories(file.getParent());
        Files.write(file, lines);
    }

    private String[] splitCsv(String line) {
        return line.split(",", -1);
    }

    private String escapeCsv(String s) {
        return s.replace(",", " ");
    }

    public synchronized int nextId(List<Product> products) {
        return products.stream().map(p -> p.id).max(Integer::compareTo).orElse(0) + 1;
    }
}
