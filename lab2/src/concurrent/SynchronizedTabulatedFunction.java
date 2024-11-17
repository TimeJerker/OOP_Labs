package concurrent;

import function.Point;
import function.TabulatedFunction;
import operations.TabulatedFunctionOperationService;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SynchronizedTabulatedFunction implements TabulatedFunction {

    final TabulatedFunction function;

    public SynchronizedTabulatedFunction(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public int getCount() {
        synchronized (function) {
            return this.function.getCount();
        }
    }

    @Override
    public double getX(int index) {
        synchronized (function) {
            return this.function.getX(index);
        }
    }

    @Override
    public double getY(int index) {
        synchronized (function) {
            return this.function.getY(index);
        }
    }

    @Override
    public void setY(int index, double value) {
        synchronized (function) {
            this.function.setY(index, value);
        }
    }

    @Override
    public int indexOfX(double x) {
        synchronized (function) {
            return this.function.indexOfX(x);
        }
    }

    @Override
    public int indexOfY(double y) {
        synchronized (function) {
            return this.function.indexOfY(y);
        }
    }

    @Override
    public double leftBound() {
        synchronized (function) {
            return this.function.leftBound();
        }
    }

    @Override
    public double rightBound() {
        synchronized (function) {
            return this.function.rightBound();
        }
    }

    @Override
    public double apply(double x) {
        synchronized (function) {
            return this.function.apply(x);
        }
    }

    @NotNull
    @Override
    public Iterator<Point> iterator() {
        synchronized (function) {
            Point[] points = TabulatedFunctionOperationService.asPoints(this.function);

            return new Iterator<>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < getCount();
                }

                @Override
                public Point next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    Point point = new Point(points[i].x, points[i].y);
                    i++;
                    return point;
                }
            };
        }
    }
}
