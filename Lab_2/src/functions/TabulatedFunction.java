package functions;

public class TabulatedFunction {
    private FunctionPoint[] array_point;
    private int pointsCount;

    TabulatedFunction(double leftX, double rightX, int pointsCount){
        this.pointsCount = pointsCount;
        array_point = new FunctionPoint[pointsCount];
        double path = Math.abs(rightX - leftX + 1) / pointsCount;
        for(int i = 0; i < array_point.length; i++){
            array_point[i].setX(leftX);
            leftX += path;
        }
    }
    TabulatedFunction(double leftX, double rightX, double[] values){
        pointsCount = values.length;
        array_point = new FunctionPoint[pointsCount];
        double path = Math.abs(rightX - leftX + 1) / pointsCount;
        for(int i = 0; i < array_point.length; i++){
            array_point[i].setX(leftX);
            array_point[i].setY(values[i]);
            leftX += path;
        }
    }

    double getLeftDomainBorder(){
        return array_point[0].getX();
    }

    double getRightDomainBorder(){
        return array_point[array_point.length-1].getX();
    }

    double getFunctionValue(double x){
        if(array_point.length == 0 || array_point[0].getX() > x || array_point[array_point.length-1].getX() < x){
            return Double.NaN;
        }
        else if(array_point[0].getX() == x){
            return array_point[0].getY();
        }
        else if(array_point[array_point.length - 1].getX() == x){
            return array_point[array_point.length - 1].getY();
        }
        FunctionPoint leftBound = new FunctionPoint();
        FunctionPoint rightBound = new FunctionPoint();
        for(int i = array_point.length - 1; i != 0; i --){
            if(x >= array_point[i - 1].getX()){
                leftBound = array_point[i - 1];
                rightBound = array_point[i];
                break;
            }
        }
        return getValueY(leftBound, rightBound, x);
    }
    private double getValueY(FunctionPoint leftBound, FunctionPoint rightBound, double x){
        return (x - leftBound.getX()) * (rightBound.getY()- leftBound.getY()) / (rightBound.getX() - leftBound.getX()) + leftBound.getY();
    }

    int getPointsCount(){
        return pointsCount;
    }

    FunctionPoint getPoint(int index){
        return array_point[index];
    }

    void setPoint(int index, FunctionPoint point) {
        if(index > array_point.length){
            return;
        }
        if (array_point[0].getX() > point.getX() || array_point[array_point.length - 1].getX() < point.getX()) {
            return;
        }
        else{
            array_point[index] = point;
        }
    }

    double getPointX(int index){
        return array_point[index].getX();
    }

    void setPointX(int index, double x){
        setPoint(index, new FunctionPoint(x, array_point[index].getY()));
    }

    double getPointY(int index){
        return array_point[index].getY();
    }

    void setPointY(int index, double y){
        array_point[index].setY(y);
    }
}
