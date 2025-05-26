package com.gadgetzone.services;

import com.gadgetzone.exceptions.NotFoundProductException;
import com.gadgetzone.products.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductServices {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    public Product findByName(String name) {
        for (Product p : products) {
            if (p.getNombre().equalsIgnoreCase(name.trim())) {
                return p;
            }
        }
        return null;
    }

    public Product findById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void deleteProduct(String name) throws NotFoundProductException {
        Product product = findByName(name);
        if (product != null) {
            products.remove(product);
            System.out.println("Producto eliminado correctamente.");
        } else {
            throw new NotFoundProductException("No se encontró el producto con nombre: " + name);
        }
    }

    public void loadInitialProducts() {
        addProduct(new Product("Tablet Samsung", 85000.0, 5, "Tablet Galaxy Tab A8 10.5\" 64GB WiFi"));
        addProduct(new Product("Mouse Logitech", 9500.0, 10, "Mouse inalámbrico con sensor óptico y diseño ergonómico"));
        addProduct(new Product("Auriculares Sony", 15000.0, 7, "Auriculares con cancelación de ruido y Bluetooth"));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
