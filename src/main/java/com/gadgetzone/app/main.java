package com.gadgetzone.app;

import com.gadgetzone.exceptions.CreacionProductoException;
import com.gadgetzone.exceptions.EntradaInvalidaException;
import com.gadgetzone.exceptions.ProductoNoEncontradoException;
import com.gadgetzone.exceptions.StockInsuficienteException;
import com.gadgetzone.orders.Pedido;
import com.gadgetzone.products.Libro;
import com.gadgetzone.products.Producto;
import com.gadgetzone.products.Ropa;
import com.gadgetzone.products.Tecnologia;
import com.gadgetzone.services.PedidoService;
import com.gadgetzone.services.ProductoService;
import com.gadgetzone.util.ValidadorEntrada;

import java.util.Scanner;

public class Main {

    private static ProductoService productoService = new ProductoService();
    private static Scanner scanner = new Scanner(System.in);
    private static PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {

        productoService.cargarProductosIniciales();
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
                    productoService.listarProductos();
                    break;
                case "3":
                    buscarYActualizarProducto();
                    break;
                case "4":
                    eliminarProducto();
                    break;
                case "5":
                    pedidoService.crearPedido(scanner, productoService);
                    break;
                case "6":
                    pedidoService.mostrarPedidos();
                    break;
                case "7":
                    salir = true;
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println();
        }
    }

    private static void eliminarProducto() {

        System.out.print("Ingrese el nombre del producto a eliminar: ");
        String nombreEliminar = scanner.nextLine();
        try {
            productoService.eliminarProducto(nombreEliminar);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarYActualizarProducto() {

        try {
            System.out.print("Ingrese el nombre del producto a buscar: ");
            String nombre = scanner.nextLine();

            Producto producto = productoService.buscarPorNombre(nombre);
            if (producto == null) {
                throw new ProductoNoEncontradoException("No se encontró un producto con ese nombre.");
            }

            System.out.println("Producto encontrado:");
            System.out.println(producto);

            System.out.println("\n¿Qué desea actualizar?");
            System.out.println("1) Nombre");
            System.out.println("2) Precio");
            System.out.println("3) Stock");
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
                default:
                    System.out.println("Opción inválida.");
                    return;
            }

            System.out.println("Producto actualizado exitosamente.");

        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: El precio o stock deben ser valores numéricos.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private static void agregarProducto() {

        try {
            System.out.println("Seleccione el tipo de producto:");
            System.out.println("1) Libro");
            System.out.println("2) Tecnología");
            System.out.println("3) Ropa");
            System.out.print("Opción: ");
            String tipo = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = ValidadorEntrada.validarTexto(scanner.nextLine(), "nombre");

            System.out.print("Precio: ");
            double precio = ValidadorEntrada.validarDouble(scanner.nextLine(), "precio");

            System.out.print("Cantidad en stock: ");
            int stock = ValidadorEntrada.validarEntero(scanner.nextLine(), "stock");

            switch (tipo) {
                case "1":
                    System.out.print("Autor: ");
                    String autor = ValidadorEntrada.validarTexto(scanner.nextLine(), "autor");
                    System.out.print("Género: ");
                    String genero = ValidadorEntrada.validarTexto(scanner.nextLine(), "genero");
                    productoService.agregarProducto(new Libro(nombre, precio, stock, autor, genero));
                    break;
                case "2":
                    System.out.print("Marca: ");
                    String marca = ValidadorEntrada.validarTexto(scanner.nextLine(), "marca");
                    System.out.print("Modelo: ");
                    String modelo = ValidadorEntrada.validarTexto(scanner.nextLine(), "modelo");
                    productoService.agregarProducto(new Tecnologia(nombre, precio, stock, marca, modelo));
                    break;
                case "3":
                    System.out.print("Color: ");
                    String color = ValidadorEntrada.validarTexto(scanner.nextLine(), "color");
                    System.out.print("Talla: ");
                    String talla = ValidadorEntrada.validarTexto(scanner.nextLine(), "talla");
                    productoService.agregarProducto(new Ropa(nombre, precio, stock, color, talla));
                    break;
                default:
                    throw new CreacionProductoException("Tipo de producto no válido.");
            }

            System.out.println("Producto agregado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Error: El precio y el stock deben ser numéricos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en los datos: " + e.getMessage());
        } catch (CreacionProductoException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
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


}
