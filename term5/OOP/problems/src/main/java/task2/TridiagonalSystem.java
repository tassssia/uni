package task2;

public class TridiagonalSystem {
    private final double[] main;
    private final double[] upper;
    private final double[] lower;
    private final double[] values;

    private int size;
    private double[] alpha;
    private double[] beta;
    private double[] z;
    private double[] y;

    public TridiagonalSystem(double[] main, double[] upper, double[] lower, double[] values) {
        this.main = main;
        this.upper = upper;
        this.lower = lower;
        this.values = values;

        size = upper.length;
        alpha = new double[size];
        beta = new double[size];
        z = new double[size];
        y = new double[size + 1];
    }

    public synchronized void forwardStep(int step) {
        if (step > size - 1 || step < 0) throw new IndexOutOfBoundsException();

        if (step == 0) {
            alpha[0] = - upper[0] / main[0];
            beta[0] = values[0] / main[0];
        } else {
            alpha[step] = upper[step] / z[step - 1];
            beta[step] = (-values[step] + lower[step - 1] * beta[step - 1]) / z[step - 1];
        }

        z[step] = -main[step + 1] - lower[step] * alpha[step];
    }

    public synchronized void backwardsStep(int step) {
        if (step > size || step < 0) throw new IndexOutOfBoundsException();

        y[step] = step == size ? (-values[size] + lower[size - 1] * beta[size - 1]) / z[size - 1] :
                alpha[step] * y[step + 1] + beta[step];
    }

    public double[] getAlpha() {
        return alpha;
    }
    public double[] getBeta() {
        return beta;
    }
    public double[] getZ() {
        return z;
    }

    public double[] getY() {
        return y;
    }

    public int getSize() {
        return size + 1;
        // for simplicity of formulas, we save as size of lower and upper diagonals, but the actual size is size+1
    }
}
