import java.util.HashMap;
import java.util.Map;

public class DataAnonyimzation {

    // Example mapping to pseudonyms
    private static final Map<String, String> mapPseudonym = new HashMap<>();
    static {
        mapPseudonym.put("John Doe", "Anonymized User1");
        mapPseudonym.put("Jane Smith", "Anonymized User2");
    }

    // Example range for age generalization
    private static final int AGE_RANGE = 5;

    // Example data masking characters
    private static final char MASK_CHAR = 'X';

    // Simple data masking: Replace characters with MASK_CHAR
    public static String maskData(String data) {
        return data.replaceAll(".", String.valueOf(MASK_CHAR));
    }

    // Replace original names with pseudonyms from the map
    public static String pseudonymizeData(String data) {
        return mapPseudonym.getOrDefault(data, data);
    }

    // Generalize age to a range (e.g., 25 - 29)
    public static int generalizeAge(int age) {
        int lowerBound = (age / AGE_RANGE) * AGE_RANGE;
        int upperBound = lowerBound + AGE_RANGE - 1;
        return lowerBound + AGE_RANGE / 2; // Return the middle value of the range
    }

    public static void main(String[] args) {
        String name = "John Doe";
        int age = 27;

        // Anonymize data using different techniques
        String maskedName = maskData(name);
        String pseudonymizedName = pseudonymizeData(name);
        int generalizedAge = generalizeAge(age);

        // Print the anonymized data
        System.out.println("Original Name: " + name);
        System.out.println("Masked Name: " + maskedName);
        System.out.println("Pseudonymized Name: " + pseudonymizedName);
        System.out.println("Generalized Age: " + generalizedAge);
    }
}
