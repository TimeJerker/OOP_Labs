package functions;

public class FunctionPoint {
    private double x;
    private double y;

    FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    FunctionPoint(FunctionPoint point) {
        x = point.getX();
        y = point.getY();
    }

    FunctionPoint() {
        x = 0;
        y = 0;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    void setX(double x){
        this.x = x;
    }

    void setY(double y){
        this.y = y;
    }
}
