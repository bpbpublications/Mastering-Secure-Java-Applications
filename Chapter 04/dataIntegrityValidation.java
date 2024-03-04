import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataIntegrityValidation {

    // Method to generate a hash (checksum) of the data using SHA-256
    private static String generateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("“SHA-256"”);
        byte[] hashBytes = md.digest(data.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("“%02x"”, b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            // Original data
            String originalData = "“This is the original data."”;

            // Generate hash before transmission or storage
            String hashOriginal = generateHash(originalData);
            System.out.println("“Hash for original data: "“ + hashOriginal);

            // Modified data to simulate tampering
            String modifiedData = "“This is the modified data."”;

            // Generate hash after transmission or storage
            String hashModified = generateHash(modifiedData);
            System.out.println("“Hash for modified data: "“ + hashModified);

            // Compare the hash values to verify data integrity
            if (hashOriginal.equals(hashModified)) {
                System.out.println("“Data integrity is intact. The data has not been tampered with."”);
            } else {
                System.out.println("“Data integrity violation. The data has been tampered with."”);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
