package com.gadgetzone.orders;

import com.gadgetzone.products.Product;
import com.gadgetzone.exceptions.InsufficientStockException;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int contador = 1;
    private int id;
    private List<ItemOrder> items;

    public Order() {
        this.id = contador++;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<ItemOrder> getItems() {
        return items;
    }

    public void agregarItem(Product producto, int cantidad) throws InsufficientStockException {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (producto.getCantidadEnStock() < cantidad) {
            throw new InsufficientStockException("No hay suficiente stock para el producto: " + producto.getNombre());
        }

        // descuento el stock al aceptar
        producto.setCantidadEnStock(producto.getCantidadEnStock() - cantidad);

        items.add(new ItemOrder(producto, cantidad));
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemOrder item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void mostrarDetalle() {
        System.out.println("Pedido ID: " + id);
        if (items.isEmpty()) {
            System.out.println("El pedido no contiene productos.");
        } else {
            for (ItemOrder item : items) {
                System.out.println(item);
            }
            System.out.println("TOTAL: $" + calcularTotal());
        }
    }
}
