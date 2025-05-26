package com.gadgetzone.app;

import com.gadgetzone.exceptions.*;
import com.gadgetzone.products.Product;
import com.gadgetzone.services.OrderService;
import com.gadgetzone.services.ProductServices;
import com.gadgetzone.util.InputValidator;

import java.util.Scanner;

public class Main {

    private static final ProductServices productServices = new ProductServices();
    private static final OrderService orderService = new OrderService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        productServices.loadInitialProducts();
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    agregarProducto();
                    break;
                case "2":
                    productServices.listProducts();
                    break;
                case "3":
                    buscarYActualizarProducto();
                    break;
                case "4":
                    eliminarProducto();
                    break;
                case "5":
                    orderService.createOrder(scanner, productServices);
                    break;
                case "6":
                    orderService.showOrders();
                    break;
                case "7":
                    salir = true;
                    System.out.println("Gracias por usar GadgetZone.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println();
        }
    }

    private static void mostrarMenu() {
        System.out.println("---- Menú Principal ----");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
    }

    private static void agregarProducto() {
        try {
            System.out.print("Nombre: ");
            String nombre = InputValidator.validarTexto(scanner.nextLine(), "nombre");

            System.out.print("Precio: ");
            double precio = InputValidator.validarDouble(scanner.nextLine(), "precio");

            System.out.print("Stock: ");
            int stock = InputValidator.validarEntero(scanner.nextLine(), "stock");

            System.out.print("Descripción: ");
            String descripcion = InputValidator.validarTexto(scanner.nextLine(), "descripción");

            Product producto = new Product(nombre, precio, stock, descripcion);
            productServices.addProduct(producto);

            System.out.println("Producto agregado correctamente.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }


    private static void buscarYActualizarProducto() {
        try {
            System.out.print("Ingrese el nombre del producto a buscar: ");
            String nombre = scanner.nextLine();

            Product producto = productServices.findByName(nombre);
            if (producto == null) {
                throw new NotFoundProductException("No se encontró un producto con ese nombre.");
            }

            System.out.println("Producto encontrado:");
            System.out.println(producto);

            System.out.println("\n¿Qué desea actualizar?");
            System.out.println("1) Nombre");
            System.out.println("2) Precio");
            System.out.println("3) Stock");
            System.out.println("4) Descripcion");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    producto.setNombre(nuevoNombre);
                    break;
                case "2":
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                    producto.setPrecio(nuevoPrecio);
                    break;
                case "3":
                    System.out.print("Nuevo stock: ");
                    int nuevoStock = Integer.parseInt(scanner.nextLine());
                    producto.setCantidadEnStock(nuevoStock);
                    break;
                case "4":
                    System.out.print("Nueva descripcion: ");
                    String nuevaDescripcion = scanner.nextLine();
                    producto.setDescripcion(nuevaDescripcion);
                    break;
                default:
                    System.out.println("Opción inválida.");
                    return;
            }

            System.out.println("Producto actualizado exitosamente.");

        } catch (NotFoundProductException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: El precio o stock deben ser valores numéricos.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        System.out.print("Ingrese el nombre del producto a eliminar: ");
        String nombreEliminar = scanner.nextLine();
        try {
            productServices.deleteProduct(nombreEliminar);
        } catch (NotFoundProductException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
