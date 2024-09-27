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


}
