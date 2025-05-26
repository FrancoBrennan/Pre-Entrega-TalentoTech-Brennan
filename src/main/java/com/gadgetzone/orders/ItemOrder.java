package com.gadgetzone.orders;

import com.gadgetzone.products.Product;

public class ItemOrder {

    private Product producto;
    private int cantidad;

    public ItemOrder(Product producto, int cantidad) {
        setProducto(producto);
        setCantidad(cantidad);
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return "Producto: " + producto.getNombre() + " | Cantidad: " + cantidad + " | Subtotal: $" + calcularSubtotal();
    }
}
