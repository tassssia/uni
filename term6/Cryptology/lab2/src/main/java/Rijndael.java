import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rijndael {
    public static List<byte[]> encrypt(String data, String keyString) {
        byte[] key = keyString.getBytes();
        byte[] dataBytes = data.getBytes();

        Rijndael forEncryption = new Rijndael();
        List<byte[]> chunks = getChunks(dataBytes);
        List<byte[]> res = new ArrayList<>();

        for (byte[] curr : chunks) {
            res.add(forEncryption.encryptParts(key, curr));
        }

        return res;
    }
    public byte[] encryptParts(byte[] key, byte[] data) {
        RoundKeysGenerator rkg = new RoundKeysGenerator(key);
        RijndaelState state = new RijndaelState(data, rkg.keys);

        state.round = 0;
        final int numberOfRounds = 14;

        state.addRoundKey(state.getKey(state.round));

        for (state.round = 1; state.round < numberOfRounds; state.round++) {
            state.subBytes();
            state.shiftRows();
            state.mixColumns();
            state.addRoundKey(state.getKey(state.round));
        }
        state.subBytes();
        state.shiftRows();
        state.addRoundKey(state.getKey(state.round));

        return state.getData();
    }

    public static String decrypt(List<byte[]> encodedData, String keyString) {
        byte[] key = keyString.getBytes();

        Rijndael forDecryption = new Rijndael();
        StringBuilder res = new StringBuilder();

        for (byte[] tmp : encodedData) {
            byte[] decryptedBytes = forDecryption.decryptParts(key, tmp);
            res.append(new String(decryptedBytes, StandardCharsets.UTF_8));
        }

        return res.toString();
    }
    public byte[] decryptParts(byte[] key, byte[] data) {
        RoundKeysGenerator rkg = new RoundKeysGenerator(key);
        RijndaelState state = new RijndaelState(data, rkg.keys);

        final int numberOfRounds = state.round = 14;

        state.addRoundKey(state.getKey(state.round));

        for (state.round = numberOfRounds - 1; state.round > 0; state.round--) {
            state.inverseShiftRows();
            state.inverseSubBytes();
            state.addRoundKey(state.getKey(state.round));
            state.inverseMixColumns();
        }
        state.inverseShiftRows();
        state.inverseSubBytes();
        state.addRoundKey(state.getKey(state.round));

        return state.getData();
    }

    private static List<byte[]> getChunks(byte[] dataBytes) {
        List<byte[]> res = new ArrayList<>();

        for (int i = 0; i < dataBytes.length; i += 16) {
            byte[] curr = Arrays.copyOfRange(dataBytes, i, Math.min(dataBytes.length, i + 16));
            byte[] tmp;

            if (curr.length <= 16) {
                tmp = new byte[16];
                System.arraycopy(curr, 0, tmp, 0, curr.length);
            } else {
                tmp = dataBytes;
            }

            res.add(tmp);
        }
        return res;
    }

    public static boolean isCorrect(String base, String decoded) {
        try {
            for (int i = 0; i < base.length(); i++) {
                if (base.charAt(i) != decoded.charAt(i)) return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}