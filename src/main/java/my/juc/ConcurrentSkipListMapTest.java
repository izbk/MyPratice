package my.juc;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * ConcurrentSkipListMap是线程安全的有序的哈希表，适用于高并发的场景。
 * @author zbk
 * @date 2020/5/19 14:43
 */
public class ConcurrentSkipListMapTest {
    // TODO: map是TreeMap对象时，程序会出错。
    //private static Map<String, String> map = new TreeMap<String, String>();
    private static Map<String, String> map = new ConcurrentSkipListMap<String, String>();

    public static void main(String[] args) {

        /**同时启动两个线程对map进行操作*/
        new MyThread("a").start();
        new MyThread("b").start();
    }

    private static void printAll() {
        map.forEach((k, v) -> System.out.print("(" + k + ", " + v + "), "));
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            int i = 0;
            while (i++ < 6) {
                String val = Thread.currentThread().getName() + i;
                map.put(val, "0");
                printAll();
            }
        }
    }
}
