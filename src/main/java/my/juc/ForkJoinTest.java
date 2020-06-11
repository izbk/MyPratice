package my.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author zbk
 * @date 2020/5/15 12:31
 */
public class ForkJoinTest extends RecursiveTask<Integer> {
    // 定义子任务直接计算的阈值
    private static final int THREAD_HOLD = 2;

    private int start;
    private int end;

    public ForkJoinTest(int start,int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算
        boolean canCompute = (end - start) <= THREAD_HOLD;
        if(canCompute){
            for(int i=start;i<=end;i++){
                sum += i;
            }
        }else{
            int middle = (start + end) / 2;
            ForkJoinTest left = new ForkJoinTest(start,middle);
            ForkJoinTest right = new ForkJoinTest(middle+1,end);
            //执行子任务
            left.fork();
            right.fork();
            //获取子任务结果
            int lResult = left.join();
            int rResult = right.join();
            sum = lResult + rResult;
        }
        return sum;
    }

    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTest task = new ForkJoinTest(1,4);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
