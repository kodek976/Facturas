import java.util.Scanner;

public class LectorConsola {
    public Inquilino leerInquilino() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== GENERADOR DE FACTURAS ===");
        System.out.println();

        System.out.print("Nombre de la empresa: ");
        String nombre = scanner.nextLine();

        System.out.print("CIF: ");
        String cif = scanner.nextLine();

        System.out.print("Fecha (dd/mm/aaaa): ");
        String fecha = scanner.nextLine();

        System.out.print("Renta (€): ");
        double renta = scanner.nextDouble();

        System.out.print("Comunidad (€): ");
        double comunidad = scanner.nextDouble();

        System.out.print("Otros conceptos (€): ");
        double otros = scanner.nextDouble();

        scanner.close();
        return new Inquilino(nombre, cif, fecha, renta, comunidad, otros);
    };
    }
