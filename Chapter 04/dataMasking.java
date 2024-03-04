// Implement Data Masking logic
public class DataMasking {
    public static String maskCreditCardNumber(String creditCardNumber) {
        String mask = "XXXX-XXXX-XXXX-";
        return mask + (creditCardNumber.substring(creditCardNumber.length() - 4));
    }
}
// Apply Data Masking
public class Main {
    public static void main(String[] args) {
        String creditCardNumber = "1234-5678-9012-3456";
        String maskedCreditCardNumber = DataMasking.maskCreditCardNumber(creditCardNumber);
        System.out.println("Original Credit Card: " + creditCardNumber);
        System.out.println("Masked Credit Card: " + maskedCreditCardNumber);
    }
}
