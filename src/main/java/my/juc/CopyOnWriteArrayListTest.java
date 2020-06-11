package my.juc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zbk
 * @date 2020/5/19 16:00
 */
public class CopyOnWriteArrayListTest {
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main( String[] args ) throws InterruptedException
    {
        arrayList.add("hello");
        arrayList.add("Java");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("hangzhou");

        Thread threadOne = new Thread(()-> {
                //修改list中下标为1的元素为JavaSe
                arrayList.set(1, "JavaSe");
                //删除元素
                arrayList.remove(2);
                arrayList.remove(3);
        });

        //保证在修改线程启动前获取迭代器
        Iterator<String> itr = arrayList.iterator();

        //启动线程
        threadOne.start();
        //等在子线程执行完毕
        threadOne.join();

        //迭代元素
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
