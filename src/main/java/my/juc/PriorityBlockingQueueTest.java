package my.juc;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author zbk
 * @date 2020/5/21 15:39
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        priorityBlockingQueue.add(1);
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(5);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(10);
        priorityBlockingQueue.add(7);
        while (!priorityBlockingQueue.isEmpty()){
            System.out.println(priorityBlockingQueue.take());
        }
    }
}
