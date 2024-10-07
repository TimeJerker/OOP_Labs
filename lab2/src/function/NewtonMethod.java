package function;

public class NewtonMethod {
    public double function(double x){
        //функция
        return 0;
    }
    public double derivative(double x){
        //производная функции
        return 0;
    }
    public double findRoot(double x0, double epsilon){
        double x1;
        do{
            x1 = x0 - function(x0) / derivative(x0);
            x0 = x1;
        } while (Math.abs(function(x1)) > epsilon);
        return x1;
    }
}
