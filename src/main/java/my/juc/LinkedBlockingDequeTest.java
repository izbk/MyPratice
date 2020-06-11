package my.juc;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 本例用BlockingDeque模拟TCP的单工通道。正确情况下发送者按顺序将数据追加到Deque的左边，
 * 当发生紧急情况时，发送者将数据追加到Deque的右边。接收者总是从队列的右边读取数据。
 * @author zbk
 * @date 2020/5/22 14:26
 */
public class LinkedBlockingDequeTest {
    public static void main(String[] args) {
        BlockingDeque<DataItem> queue = new LinkedBlockingDeque<DataItem>(20);

        try {
            for (int i = 0; i < 10; i++) {
                DataItem item = new DataItem(i);
                if (item.isUrgency()) {
                    queue.putLast(item);
                } else {
                    queue.putFirst(item);
                }
            }
        } catch (InterruptedException ie) {
            System.out.println("Interrupted");
        }

        Thread receiver = new Thread(new Receiver(queue));
        receiver.start();

        while (DataItem.count.get() > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        receiver.interrupt();
        System.out.println("Main finished");
    }
}

class DataItem {
    private final int number;
    private final boolean flag;

    protected static final AtomicInteger count = new AtomicInteger(0);

    public DataItem(int number) {
        this.number = number;
        if ((number % 3) == 0) {
            flag = true;
        } else {
            flag = false;
        }
        count.incrementAndGet();
    }

    public void show() {
        System.out.println(number + ": " + ((flag == true) ? "Urgency" : "Common"));
    }

    public boolean isUrgency() {
        return flag;
    }
}

class Receiver extends Thread {
    private final BlockingDeque<DataItem> queue;

    public Receiver(BlockingDeque<DataItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        DataItem item;
        try {
            while (true) {
                // 从队尾获取元素
                item = queue.takeLast();
                item.show();
                DataItem.count.decrementAndGet();
            }
        } catch (InterruptedException ie) {
            System.out.println("Receiver finished");
        }
    }
}