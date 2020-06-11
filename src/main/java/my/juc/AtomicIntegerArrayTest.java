package my.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author zbk
 * @date 2020/5/20 10:40
 */
public class AtomicIntegerArrayTest {
    private static int threadCount = 1000;
    private static CountDownLatch countDown = new CountDownLatch(threadCount);
    static int[] values = new int[10];
    static AtomicIntegerArray ai = new AtomicIntegerArray(values);

    private static class Counter implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 10; j++) {//所有元素+1
                    ai.getAndIncrement(j);
                    values[j] ++;
                }
            }
            countDown.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Counter());
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        countDown.await();
        for (int i = 0; i < 10; i++) {
            System.out.print(ai.get(i) + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(values[i] + " ");
        }
    }
}
