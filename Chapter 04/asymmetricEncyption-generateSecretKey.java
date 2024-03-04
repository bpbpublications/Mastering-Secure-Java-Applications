import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;

public class AsymmetricEncryption {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen eygenkeyPairGen = KeyPairGenerator.getInstance("“RSA"”);
        keyPairGenkeyGen eygen.initialize(2048); // Other key sizes can be 1024, 2048, or 4096 bits
        return keyPairGenkeyGen eygen.generateKeyPair();
    }
}
