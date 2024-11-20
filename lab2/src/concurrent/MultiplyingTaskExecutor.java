package concurrent;

import function.ArrayTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.TabulatedFunction;
import function.UnitFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {
    public static void main(String[] args){
        TabulatedFunction tFunc = new LinkedListTabulatedFunction(new UnitFunction(), 1, 1000, 1000);
        List<Thread> listThreads = new ArrayList<>();

        for(int i = 0; i <= 10; i++){
            MultiplyingTask mt = new MultiplyingTask(tFunc);
            Thread thread = new Thread(mt);
            listThreads.add(thread);
        }

        for(Thread thread : listThreads){
            thread.start();
        }

        while (!listThreads.isEmpty()) {
            listThreads.removeIf(thread -> !thread.isAlive());
        }

        System.out.println(tFunc);
    }
}
