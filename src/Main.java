import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Kailua Car Rental!");
        Menu.setPasswords();
        Menu.menuSelection();
        System.out.println("Goodbye!");
    }
}