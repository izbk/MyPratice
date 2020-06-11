package my.juc;

import lombok.ToString;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zbk
 * @date 2020/5/21 14:11
 */
public class ArrayBlockingQueueTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        int capacity = 10;
        ArrayBlockingQueue<Bread> queue = new ArrayBlockingQueue<Bread>(capacity);
        for (int i = 0; i < 2; i++) {
            new Thread(new Producer(queue)).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new Consumer(queue)).start();
        }
    }
}

/**
 * 生产者
 *
 * @author 百恼 2013-11-16下午07:44:36
 */
class Producer implements Runnable {
    private final ArrayBlockingQueue<Bread> queue;
    public Producer(ArrayBlockingQueue<Bread> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            produce();
        }
    }

    public void produce() {
        /**
         * put()方法是如果容器满了的话就会把当前线程挂起
         * offer()方法是容器如果满的话就会返回false，也正是我在前一篇中实现的那种策略。
         */
        try {
            Bread bread = new Bread();
            queue.put(bread);
            System.out.println("Producer:" + bread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 消费者
 *
 * @author 百恼  2013-11-16下午07:42:08
 */
class Consumer implements Runnable {
    private final ArrayBlockingQueue<Bread> queue;
    public Consumer(ArrayBlockingQueue<Bread> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            consume();
        }
    }

    public void consume() {
        /**
         * take()方法和put()方法是对应的，从中拿一个数据，如果拿不到线程挂起
         * poll()方法和offer()方法是对应的，从中拿一个数据，如果没有直接返回null
         */
        try {
            Bread bread = queue.take();
            System.out.println("consumer:" + bread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@ToString
class Bread {
}
