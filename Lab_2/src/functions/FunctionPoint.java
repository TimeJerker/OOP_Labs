package functions;

public class FunctionPoint {
    private double x;
    private double y;
    FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    FunctionPoint(FunctionPoint point){
        x = point.setX();
        y = point.setY();
    }
    FunctionPoint(){
        x = 0;
        y = 0;
    }
    double setX(){
        return x;
    }
    double setY(){
        return y;
    }
}
