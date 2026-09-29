import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SISTEMA DE ACCESO ===");

        System.out.print("Ingresa tu edad: ");
        int edad = scanner.nextInt();

        if (edad < 18) {
            System.out.println("Acceso denegado.");
            System.out.println("Debes ser mayor de edad.");

        } else if (edad >= 18 && edad < 65) {
            System.out.println("Acceso permitido.");
            System.out.println("Bienvenido al evento.");

        } else {
            System.out.println("Acceso permitido.");
            System.out.println("Tienes acceso preferente.");
        }

        scanner.close();
    }
}