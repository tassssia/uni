public class RoundKeysGenerator {
    byte[][] keys;

    public RoundKeysGenerator(byte[] key) {
        final int n = 32;
        final int b = 240;
        byte[] schedule = new byte[n - 16 + b];

        for (int i = 0; i < n; ++i) {
            schedule[i] = key[i % key.length];
        }

        int bytesGenerated = n;
        int rconIteration = 1;

        scheduleGenerating(n, b, schedule, bytesGenerated, rconIteration);

        this.keys = new byte[16][16];
        for (int i = 0; i < schedule.length; ++i) {
            this.keys[i / 16][i % 16] = schedule[i];
        }
    }

    private void scheduleGenerating(int n, int b, byte[] schedule, int bytesGenerated, int rconIteration) {
        while (bytesGenerated < b) {
            byte[] tmp = new byte[4];
            System.arraycopy(schedule, bytesGenerated - 4, tmp, 0, 4);

            if (bytesGenerated % n == 0) {
                singleKeyGenerating(tmp, rconIteration);
                rconIteration += 1;
            }
            else {
                for (int i = 0; i < 4; ++i) {
                    tmp[i] = (byte) Constants.S_BOX[Byte.toUnsignedInt(tmp[i])];
                }
            }

            for (int i = 0; i < 4; ++i) {
                schedule[bytesGenerated + i] = (byte) (tmp[i] ^ schedule[bytesGenerated + i - n]);
            }
            bytesGenerated += 4;

            for (int k = 0; k < 3; ++k) {
                System.arraycopy(schedule, bytesGenerated - 4, tmp, 0, 4);
                for (int i = 0; i < 4; ++i) {
                    schedule[bytesGenerated + i] = (byte) (tmp[i] ^ schedule[bytesGenerated + i - n]);
                }
                bytesGenerated += 4;
            }
        }
    }
    private void singleKeyGenerating(byte[] in, int round) {
        byte low = in[0];
        in[0] = in[1];
        in[1] = in[2];
        in[2] = in[3];
        in[3] = low;

        in[0] = (byte) Constants.S_BOX[Byte.toUnsignedInt(in[0])];
        in[1] = (byte) Constants.S_BOX[Byte.toUnsignedInt(in[1])];
        in[2] = (byte) Constants.S_BOX[Byte.toUnsignedInt(in[2])];
        in[3] = (byte) Constants.S_BOX[Byte.toUnsignedInt(in[3])];

        in[0] ^= (byte) Constants.RCON[round];
    }
}