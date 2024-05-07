import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        ElGamal elGamal = new ElGamal();
        SecureRandom random = new SecureRandom();

        BigInteger plaintext = new BigInteger(32, random);
        System.out.println("Plaintext: " + plaintext);
        BigInteger ciphertext = elGamal.encrypt(plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        BigInteger decryptedText = elGamal.decrypt(ciphertext);
        System.out.println("Decrypted text: " + decryptedText);
    }
}
