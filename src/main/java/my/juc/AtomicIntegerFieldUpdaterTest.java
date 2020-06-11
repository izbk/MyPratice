package my.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author zbk
 * @date 2020/5/20 10:50
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) throws InterruptedException {
        BackAccount account = new BackAccount(0);
        List<Thread> list = new ArrayList<>();
        for(int i = 0; i < 1000 ; i++) {
            Thread t = new Thread(new Task(account));
            list.add(t);
            t.start();
        }
        for(Thread t : list) {
            t.join();
        }
        System.out.println(account.getMoney());
    }


}

class Task implements Runnable {
    private BackAccount backAccount;

    Task(BackAccount account) {
        this.backAccount = account;
    }

    @Override
    public void run() {
        backAccount.incMoney();
    }
}

class BackAccount {
    // 更新的字段必须是volatile修饰的
    // 否则报错：java.lang.IllegalArgumentException: Must be volatile type
    private volatile int money;
    private static final AtomicIntegerFieldUpdater<BackAccount> updater = AtomicIntegerFieldUpdater.newUpdater(BackAccount.class, "money");

    BackAccount(int money) {
        this.money =  money;
    }

    public void incMoney() {
        // 通过AtomicIntegerFieldUpdater操作字段
        updater.incrementAndGet(this);
    }

    public int getMoney() {
        return money;
    }
}