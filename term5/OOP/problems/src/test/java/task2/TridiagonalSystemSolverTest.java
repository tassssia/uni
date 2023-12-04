package task2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TridiagonalSystemSolverTest {
    private static double EPS = 1e-6;
    private static TridiagonalSystem system;
    private static int n;

    private static final double[] expectedAlpha = {1, -1, 0.5, -0.5714285714285714};
    private static final double[] expectedBeta = {-3, 3, -1.5, 0.7142857142857143};
    private static final double[] expectedZ = {2, 2, -3.5, -2.428571428571429};
    private static final double[] expectedY = {2, 5, -2, -1, 3};

    @BeforeEach
    void setUp() {
        double[] main = {2, -3, -3, 3, 3};
        double[] upper = {-2, -2, 1, 2};
        double[] lower = {1, -1, 1, 1};
        double[] values = {-6, -9, 0, 1, 8};

        system = new TridiagonalSystem(main, upper, lower, values);
        n = system.getSize() - 1;
    }

    @Test
    void testSequentially() {

        for (int step = 0; step < n; step++) {
            system.forwardStep(step);
        }
        assertArrayEquals(expectedAlpha, system.getAlpha(), EPS);
        assertArrayEquals(expectedBeta, system.getBeta(), EPS);
        assertArrayEquals(expectedZ, system.getZ(), EPS);

        for (int step = n; step >= 0; step--) {
            system.backwardsStep(step);
        }
        assertArrayEquals(expectedY, system.getY(), EPS);
    }

    @Test
    void testConcurrently() {
        double[] actualY = TridiagonalSystemSolver.solve(system);

        assertArrayEquals(expectedY, actualY, EPS);
    }

    private double[] solveSequentially(TridiagonalSystem system) {
        for (int i = 0; i < system.getSize(); i++) {
            system.forwardStep(i);
        }
        for (int i = system.getSize(); i >= 0; i--) {
            system.backwardsStep(i);
        }
        return system.getY();
    }
}
