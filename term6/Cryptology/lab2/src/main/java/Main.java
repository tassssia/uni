import java.util.List;

public class Main {
    public static void main(String[] args) {
        String plaintext = "Some text for demo. Some text for demo. Some text for demo.";
        System.out.println("Plaintext: " + plaintext + "\n");
        String keyString = "TopSecretKey";

        List<byte[]> ciphertext = Rijndael.encrypt(plaintext, keyString);
        System.out.println("Ciphertext:");
        for (byte[] chunk : ciphertext) {
            System.out.println(bytesToHex(chunk));
        }

        String decryptedText = Rijndael.decrypt(ciphertext, keyString);
        System.out.println("\nDecrypted data: " + decryptedText + "\n");

       if (Rijndael.isCorrect(plaintext, decryptedText)) {
            System.out.println("Decryption was successful");
       }
       else {
           System.out.println("Oops, something went wrong...");
       }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}