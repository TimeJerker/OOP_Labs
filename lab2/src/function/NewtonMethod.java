package function;

import java.util.function.Function;

public class NewtonMethod {
    private Function<Double, Double> func;
    private Function<Double, Double> funcDer;
    private double eps;
    private double x0;

    public NewtonMethod(Function<Double, Double> func, Function<Double, Double> funcDer,  double eps, double x0){
        this.eps = eps;
        this.x0 = x0;
        this.func = func;
        this.funcDer = funcDer;
    }

    public double getx0(){
        return x0;
    }

    public double getEps(){
        return eps;
    }

    public double findRoot(double x0, double epsilon){
        double x1;
        do{
            x1 = x0 - func.apply(x0) / funcDer.apply(x0);
            x0 = x1;
        } while (Math.abs(func.apply(x1)) > epsilon);
        return x1;
    }
}