import java.util.Scanner;

public class InputValidation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your age: ");
        String inputAge = scanner.nextLine();

        if (isValidAge(inputAge)) {
            int age = Integer.parseInt(inputAge);
            System.out.println("Your age is: " + age);
        } else {
            System.out.println("Invalid age input. Please enter a valid number.");
        }
        
        scanner.close();
    }

    public static boolean isValidAge(String inputAge) {
        try {
            int age = Integer.parseInt(inputAge);
            return age >= 0 && age <= 120;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
