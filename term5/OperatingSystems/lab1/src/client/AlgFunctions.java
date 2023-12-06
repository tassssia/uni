package client;

import java.util.Optional;

public class AlgFunctions {
    public static Optional<Integer> algF(Integer x) {
        return Optional.of(2 * x + 1);
    }

    public static Optional<Integer> algG(Integer x) {
        return Optional.of(x * x);
    }
}