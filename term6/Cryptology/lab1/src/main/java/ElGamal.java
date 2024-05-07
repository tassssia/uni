import java.math.BigInteger;
import java.security.SecureRandom;

class ElGamal {
    private BigInteger primary;
    private BigInteger generator;
    private BigInteger privateKey;
    private BigInteger h;

    public ElGamal() {
        SecureRandom random = new SecureRandom();

        primary = BigInteger.probablePrime(128, random);
        generator = BigInteger.valueOf(2).modPow(BigInteger.probablePrime(64, random), primary);
        privateKey = new BigInteger(primary.bitLength() - 1, random);
        h = generator.modPow(privateKey, primary);
    }

    public BigInteger encrypt(BigInteger plaintext) {
        SecureRandom random = new SecureRandom();

        BigInteger k = new BigInteger(primary.bitLength() - 1, random);
        BigInteger a = generator.modPow(k, primary);
        BigInteger b = h.modPow(k, primary).multiply(plaintext).mod(primary);

        return a.multiply(primary).add(b); // ciphertext = a * p + b
    }
    public BigInteger decrypt(BigInteger ciphertext) {
        BigInteger a = ciphertext.divide(primary);
        BigInteger b = ciphertext.mod(primary);

        return a.modPow(privateKey.negate(), primary).multiply(b).mod(primary);
    }
}