package helpers;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoHelper {
    public static KeyPair generateKeyPair() {
        KeyPair pair = null;
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            pair = keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return pair;
    }

    public static String encryptMessage(String message, PublicKey publicKey) throws Exception{
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = encryptCipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptMessage(String message, PrivateKey privateKey)
            throws Exception {

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(message);
        byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);

    }

    public static PublicKey getPublicKeyFromMessage(String message) {
        byte[] publicKeyBytes = Base64.getDecoder().decode(message);

        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        try {
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
