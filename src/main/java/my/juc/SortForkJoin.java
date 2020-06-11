package my.juc;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author zbk
 * @date 2020/5/15 12:37
 */
public class SortForkJoin {
    /**
     * 数组排序
     *
     * @param array
     * @return
     */
    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int index = 0; index < array.length - 1; index++) {
            int pre_index = index;
            int currentValue = array[index + 1];
            while (pre_index >= 0 && array[pre_index] > currentValue) {
                array[pre_index + 1] = array[pre_index];
                pre_index--;
            }
            array[pre_index + 1] = currentValue;
        }
        return array;
    }

    /**
     * 组合
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int resultIndex = 0, leftIndex = 0, rightIndex = 0; resultIndex < result.length; resultIndex++) {
            if (leftIndex >= left.length) {
                result[resultIndex] = right[rightIndex++];
            } else if (rightIndex >= right.length) {
                result[resultIndex] = left[leftIndex++];
            } else if (left[leftIndex] > right[rightIndex]) {
                result[resultIndex] = right[rightIndex++];
            } else {
                result[resultIndex] = left[leftIndex++];
            }
        }
        return result;
    }


    static class SortTask extends RecursiveTask<int[]> {
        private int threshold;
        private int start;
        private int end;
        private int segmentation;
        private int[] src;

        public SortTask(int[] src, int start, int end, int segmentation) {
            this.src = src;
            this.start = start;
            this.end = end;
            this.threshold = src.length / segmentation;
            this.segmentation = segmentation;
        }

        @Override
        protected int[] compute() {
            if ((end - start) < threshold) {
                int mid = (end - start) / 2;
                SortTask leftTask = new SortTask(src, start, mid, segmentation);
                SortTask rightTask = new SortTask(src, mid + 1, end, segmentation);
                invokeAll(leftTask, rightTask);
                return SortForkJoin.merge(leftTask.join(), rightTask.join());
            } else {
                return SortForkJoin.sort(src);
            }
        }
    }

    /**
     * @param length
     */
    public static int[] createIntArray(int length) {
        int[] array = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = createIntArray(1000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SortTask sortTask = new SortTask(array, 0, array.length - 1, 1000);
        long start = System.currentTimeMillis();
        forkJoinPool.execute(sortTask);
        System.out.println(
                " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }

}
