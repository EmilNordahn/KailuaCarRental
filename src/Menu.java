import java.util.Scanner;

public class Menu {
    public static void MenuSelection() {
        System.out.println("What would you like to work with?\n1. Cars\n2. Customers\n3. Rental contracts\n4. Exit program");
        switch(getInt()) {
            case 1:
                System.out.println("Cars go vroom");
                break;
            case 2:

        }
    }

    public static int getInt() {
        Scanner console = new Scanner(System.in);
        while (!console.hasNextInt()) {
            System.out.println("Input wasn't an integer.");
            console.next();
        }
        return console.nextInt();
    }
}
