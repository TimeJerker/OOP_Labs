package concurrent;

import function.ArrayTabulatedFunction;
import function.TabulatedFunction;

public class MultiplyingTask implements Runnable{
    private final TabulatedFunction atf;
    public MultiplyingTask(TabulatedFunction atf){
        this.atf = atf;
    }
    public void run(){
        for(int i = 0; i < atf.getCount(); i++){
            atf.setY(i, atf.getY(i)*2);
        }
        System.out.println("ThreadUp2 has completed executing the task");
    }

}
