package com.xiaosuokeji.server.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程相关工具类
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2017/12/8
 * Time: 14:00
 */

public class ThreadPoolUtil {


    private final static int corePoolSize = 4;// 核心线程数量

    private final static int KEEP_ALIVE_TIME = 60 * 1000;// 线程存活时间：当线程数量超过corePoolSize时，60秒钟空闲即关闭线程

    private final static String CUSTOM_THREAD_NAME = "MAIL_";

    public final static ThreadPoolExecutor threadPoolExecutor;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new CustomFactory(Thread.NORM_PRIORITY));
    }

    /**
     * execute方法用于提交不需要返回值的任务，利用这种方式提交的任务无法得知是否正常执行
     * 用法：ThreadPoolUtil.execute(() ->{//执行动作});
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * submit方法用于提交一个任务并带有返回值，这个方法将返回一个Future类型对象。
     * 可以通过这个返回对象判断任务是否执行成功，并且可以通过future.get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成。
     * 如：
     *      Future<?> future=threadPoolExecutor.submit(futureTask);
     *      Object value=future.get();
     *
     * @param runnable
     * @return
     */
    public static Future submit(Runnable runnable) {
        return threadPoolExecutor.submit(runnable);
    }

    private static class CustomFactory implements ThreadFactory {

        int mPriority;
        ThreadGroup mThreadGroup;
        private AtomicInteger mInteger = new AtomicInteger(1);

        public CustomFactory(int normal) {
            this.mPriority = normal;
            mThreadGroup = Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread mThread = new Thread(mThreadGroup, r, CUSTOM_THREAD_NAME + mInteger.getAndIncrement(), 0);
            mThread.setPriority(mPriority);
            return mThread;
        }
    }
}
