package operations;

import function.MathFunction;

public interface DifferentialOperator<T> extends MathFunction {
    T derive(T function);
}
