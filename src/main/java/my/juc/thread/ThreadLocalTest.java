package my.juc.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zbk
 * @date 2020/5/15 8:51
 */
public class ThreadLocalTest {


    /**
     *    private static final ThreadLocal threadSession = new ThreadLocal();
     *     一个简单的用ThreadLocal来存储Session的例子：
     *     public static Session getSession() throws InfrastructureException {
     *         Session s = (Session) threadSession.get();
     *         try {
     *             if (s == null) {
     *                 s = getSessionFactory().openSession();
     *                 threadSession.set(s);
     *             }
     *         } catch (HibernateException ex) {
     *             throw new InfrastructureException(ex);
     *         }
     *         return s;
     *     }
     */

    /**
     * 比如Java7中的SimpleDateFormat不是线程安全的，可以用ThreadLocal来解决这个问题：
     */
    private static ThreadLocal<SimpleDateFormat> format1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String formatDate(Date date) {
        return format1.get().format(date);
    }


}
