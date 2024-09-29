package functions;

public class TabulatedFunction {
    private FunctionPoint[] array_point;
    private int pointsCount;

    public TabulatedFunction(double leftX, double rightX, int pointsCount){
        this.pointsCount = pointsCount;
        array_point = new FunctionPoint[pointsCount];
        double path = Math.abs(rightX - leftX + 1) / pointsCount;
        for(int i = 0; i != pointsCount; i++){
            array_point[i] = new FunctionPoint(leftX, 0);
            leftX += path;
        }
    }
    public TabulatedFunction(double leftX, double rightX, double[] values){
        pointsCount = values.length;
        array_point = new FunctionPoint[pointsCount];
        double path = Math.abs(rightX - leftX + 1) / pointsCount;
        for(int i = 0; i != pointsCount; i++){
            array_point[i] = new FunctionPoint(leftX, values[i]);
            leftX += path;
        }
    }

    public double getLeftDomainBorder(){
        return array_point[0].getX();
    }

    public double getRightDomainBorder(){
        return array_point[array_point.length-1].getX();
    }

    public double getFunctionValue(double x){
        if(pointsCount == 0 || array_point[0].getX() > x || array_point[pointsCount-1].getX() < x){
            return Double.NaN;
        }
        else if(array_point[0].getX() == x){
            return array_point[0].getY();
        }
        else if(array_point[pointsCount - 1].getX() == x){
            return array_point[pointsCount - 1].getY();
        }
        FunctionPoint leftBound = new FunctionPoint();
        FunctionPoint rightBound = new FunctionPoint();
        for(int i = pointsCount - 1; i != 0; i --){
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

    public int getPointsCount(){
        return pointsCount;
    }

    public FunctionPoint getPoint(int index){
        return array_point[index];
    }

    public void setPoint(int index, FunctionPoint point) {
        if(index > array_point.length){
            return;
        }
        double leftBound = index == 0 ? Double.NEGATIVE_INFINITY : array_point[index - 1].getX();
        double rightBound = index == pointsCount - 1 ? Double.POSITIVE_INFINITY : array_point[index + 1].getX();
        if (point.getX() >= leftBound && point.getX() <= rightBound)
            array_point[index] = point;
    }

    public double getPointX(int index){
        return array_point[index].getX();
    }

    public void setPointX(int index, double x){
        setPoint(index, new FunctionPoint(x, array_point[index].getY()));
    }

    public double getPointY(int index){
        return array_point[index].getY();
    }

    public void setPointY(int index, double y){
        array_point[index].setY(y);
    }

    public void deletePoint(int index){
        System.arraycopy(array_point, index+1, array_point, index, array_point.length-index-1);
        array_point[array_point.length-1] = null;
        --pointsCount;
    }
    public void addPoint(FunctionPoint point){
        if(array_point.length == pointsCount){
            FunctionPoint[] new_array = new FunctionPoint[pointsCount+1];
            System.arraycopy(array_point, 0, new_array, 0, pointsCount);
            array_point = new_array;
        }
        int key = 0;
        for(int i = pointsCount - 1; i >= 0; i--){
            if(point.getX() > array_point[i].getX()){
                key = i+1;
                break;
            }
        }
        System.arraycopy(array_point, key, array_point, key+1, pointsCount-key);
        array_point[key] = point;
        pointsCount++;
    }

}
