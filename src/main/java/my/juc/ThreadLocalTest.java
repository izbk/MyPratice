package my.juc;

/**
 * @author zbk
 * @date 2020/5/14 10:18
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> seqCount = new ThreadLocal<Integer>() {
        // 实现initialValue()
        @Override
        public Integer initialValue() {
            return 2;
        }
    };

    public int nextSeq() {
        seqCount.set(seqCount.get() + 1);
        return seqCount.get();
    }

    public static void main(String[] args) {
        ThreadLocalTest seqCount = new ThreadLocalTest();

        SeqThread thread1 = new SeqThread(seqCount);
        SeqThread thread2 = new SeqThread(seqCount);
        SeqThread thread3 = new SeqThread(seqCount);
        SeqThread thread4 = new SeqThread(seqCount);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    private static class SeqThread extends Thread {
        private ThreadLocalTest seqCount;

        SeqThread(ThreadLocalTest seqCount) {
            this.seqCount = seqCount;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + " seqCount :" + seqCount.nextSeq());
            }
        }
    }
}
