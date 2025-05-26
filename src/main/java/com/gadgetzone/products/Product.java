package com.gadgetzone.products;

public class Product {

    private static int contador = 1;
    private int id;
    private String nombre;
    private double precio;
    private int cantidadEnStock;

    private String descripcion;

    public Product(String nombre, double precio, int cantidadEnStock, String descripcion) {
        this.id = contador++;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = formatearNombre(nombre.trim());
        } else {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    protected String formatearNombre(String nombreOriginal) {

        String[] palabras = nombreOriginal.split(" ");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)));
                if (palabra.length() > 1) {
                    resultado.append(palabra.substring(1).toLowerCase());
                }
                resultado.append(" ");
            }
        }
        return resultado.toString().trim();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio > 0) {
            this.precio = precio;
        } else {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        if (cantidadEnStock >= 0) {
            this.cantidadEnStock = cantidadEnStock;
        } else {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            this.descripcion = descripcion.trim();
        } else {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
    }


    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Precio: $" + precio + " | Stock: " + cantidadEnStock +
                " | Descripción: " + descripcion;
    }

}
