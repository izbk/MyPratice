package my.java8.common;

import java.util.ArrayList;
import java.util.List;

/**
 * PECE 原则: Producer Extends, Consumer Super
 * @author zbk
 * @date 2020/5/20 9:50
 */
@SuppressWarnings("all")
public class TTest {
    public static void main(String[] args) {
        // Number 是 Number 的子类
        List<? extends Number> list1 = new ArrayList<Number>();
        // Integer 是 Number 的子类
        List<? extends Number> list2 = new ArrayList<Integer>();
        // Double 是 Number 的子类
        List<? extends Number> list3 = new ArrayList<Double>();
        /**
         * ? extends T
         * ? extends T 描述了通配符上界, 即具体的泛型参数需要满足条件: 泛型参数必须是 T 类型或它的子类
         * 关于写入：我们不能添加任何对象到 List<? extends T> 中, 因为我们不能确定一个 List<? extends T>
         *  对象实际的类型是什么, 因此就不能确定插入的元素的类型是否和这个 List 匹配. List<? extends T>
         *  关于读取：唯一能保证的是我们从这个 list 中读取的元素一定是一个 T 类型的.
         */
//        list1.add(Integer.valueOf(1));
//        list2.add(Long.valueOf(1L));
//        list3.add(Double.valueOf(1D));
            Number number = list1.get(0);

        /**
         * ? super T
         * ? super T 描述了通配符下界, 即具体的泛型参数需要满足条件: 泛型参数必须是 T 类型或它的父类
         * 关于写入：可以添加T及子类对象到集合中
         * 关于读取：唯一能够保证的是, 我们可以从 array 中获取到一个 Object 对象的实例.
         */
        // 在这里, Integer 可以认为是 Integer 的 "父类"
        List<? super Integer> array1 = new ArrayList<Integer>();
        // Number 是 Integer 的 父类
        List<? super Integer> array2 = new ArrayList<Number>();
        // Object 是 Integer 的 父类
        List<? super Integer> array3 = new ArrayList<Object>();
        array1.add(1);
//        array1.add(1L);
//        array1.add(1D);

        Object object = array1.get(0);
    }

}
