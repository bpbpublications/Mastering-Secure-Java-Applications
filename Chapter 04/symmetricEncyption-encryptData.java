import javax.crypto.Cipher;

public class SymmetricEncryption {
    public static byte[] encryptData(SecretKey secretKey, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data.getBytes());
    }
}
