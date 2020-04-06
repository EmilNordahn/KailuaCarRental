import java.util.Scanner;

public class Menu {
    public static void menuSelection() {
        System.out.println("What would you like to work with?\n1. Cars\n2. Customers\n3. Rental contracts\n4. Exit program");
        int menuChoice = getInt();
        switch(menuChoice) {
            case 1:
                carMethods();
                break;
            case 2:
                customerMethods();
                break;
            case 3:
                //contractMethods();
                break;
            case 4:
                break;
            default:
                System.out.println("Input wasn't a possible selection. Please try again.");
                menuChoice = getInt();
        }
        System.out.println("\nWould you like to do anything else?\n1. Yes\n2. No");
        if (getInt() == 1) {
            menuSelection();
        }
    }

    public static void carMethods() {
        System.out.println("What would you like to do with cars?\n1. See list of cars\n2. Update car info\n3. Create car\n4. Delete car");
        switch(getInt()) {
            case 1:
                AccessingCarDB.listCars();
                break;
            case 2:
                AccessingCarDB.updateCar();
                break;
            case 3:
                AccessingCarDB.addCar();
                break;
            case 4:
                AccessingCarDB.deleteCar();
            default:
                System.out.println("Input wasn't a possible selection. Returning to main menu.");
                break;
        }
    }

    public static void customerMethods() {
        System.out.println("What would you like to do with renters?\n1. See list of renters\n2. Update renter info\n3. Create renter\n4. Delete renter");
        switch(getInt()) {
            case 1:
                AccessingCustomerDB.listCustomers();
                break;
            case 2:
                AccessingCustomerDB.updateCustomer();
                break;
            case 3:
                AccessingCustomerDB.createNewCustomer();
                break;
            case 4:
                AccessingCustomerDB.deleteCustomer();
            default:
                System.out.println("Input wasn't a possible selection. Returning to main menu.");
                break;
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
