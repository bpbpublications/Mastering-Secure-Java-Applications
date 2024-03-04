import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SymmetricEncryption {
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // You can choose different key sizes (128, 192, or 256 bits)
        return keyGen.generateKey();
    }
}
