package my.juc.thread;

/**
 * @author zbk
 * @date 2020/5/28 16:05
 */
public class RunnableTest {
    public static void main(String[] args){
        RunnableTest runner = new RunnableTest();
        Task t = runner.new Task();
        t.run();

        new Thread(t).start();
    }
    class Task implements Runnable{
        @Override
        public void run() {
            System.out.println("run run run");
        }
    }
}
