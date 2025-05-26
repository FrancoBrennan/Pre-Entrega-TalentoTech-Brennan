package com.gadgetzone.services;

import com.gadgetzone.exceptions.InsufficientStockException;
import com.gadgetzone.exceptions.InvalidInputException;
import com.gadgetzone.orders.Order;
import com.gadgetzone.products.Product;
import com.gadgetzone.util.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {

    private final List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public void createOrder(Scanner scanner, ProductServices productServices) {
        if (productServices.getAllProducts().isEmpty()) {
            System.out.println("No hay productos disponibles para realizar un pedido.");
            return;
        }

        Order order = new Order();
        boolean continuar = true;

        while (continuar) {
            try {
                productServices.listProducts();

                System.out.print("Ingrese el ID del producto que desea agregar al pedido (o -1 para finalizar): ");
                int idProducto = InputValidator.validarEntero(scanner.nextLine(), "ID del producto");

                if (idProducto == -1) {
                    break;
                }

                Product product = productServices.findById(idProducto);
                if (product == null) {
                    System.out.println("Producto no encontrado. Intente nuevamente.");
                    continue;
                }

                System.out.print("Ingrese la cantidad deseada: ");
                int cantidad = InputValidator.validarEntero(scanner.nextLine(), "cantidad");

                order.agregarItem(product, cantidad);
                System.out.println("Producto agregado al pedido.");

                System.out.print("¿Desea agregar otro producto al pedido? (s/n): ");
                String respuesta = InputValidator.validarTexto(scanner.nextLine(), "respuesta");
                if (!respuesta.equalsIgnoreCase("s")) {
                    continuar = false;
                }

            } catch (InvalidInputException | InsufficientStockException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }


        if (order.getItems().isEmpty()) {
            System.out.println("No se agregó ningún producto. El pedido no se registrará.");
        } else {
            addOrder(order);
            System.out.println("Pedido creado exitosamente:");
            order.mostrarDetalle();
        }
    }

    public void showOrders() {
        if (orders.isEmpty()) {
            System.out.println("No se realizó ningún pedido.");
            return;
        }

        for (Order order : orders) {
            order.mostrarDetalle();
            System.out.println("------------------------");
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }
}
