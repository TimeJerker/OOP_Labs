package function;

import java.util.function.Function;

public class MethodSimpleIteration {
    private final double q; // Точность
    private final int maxIterations; // Наибольшее количество приближений
    private final Function<Double, Double> function;

    public MethodSimpleIteration(double q, int maxIterations, Function<Double, Double> function) {
        this.q = q;
        this.maxIterations = maxIterations;
        this.function = function;
    }

    public double findRoot(double initialGuess) {
        double x0 = initialGuess;
        double x1 = function.apply(x0);
        int iteration = 0;

        while (Math.abs(x1 - x0) > q && iteration < maxIterations) {
            x0 = x1;
            x1 = function.apply(x1);
            iteration++;
        }
        if(iteration == maxIterations) {
            throw new IllegalStateException("Diverges");
        }

        return x1;
    }
}