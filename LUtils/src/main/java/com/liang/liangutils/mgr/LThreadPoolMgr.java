package com.liang.liangutils.mgr;

import android.support.annotation.NonNull;
import com.liang.liangutils.able.LCustomerNoParam;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Amarao
 * CreateAt : 8:09 2019/3/16
 * Describe : 线程管理工具类
 * <p>
 * 参考：
 * https://blog.csdn.net/l540675759/article/details/62230562
 * https://blog.csdn.net/jun5753/article/details/79521635
 */
public class LThreadPoolMgr {

    private static LThreadPoolMgr mInstance;
    DefaultThreadFactory mThreadFactory; // 线程生产工厂
    /**
     * 核心线程池的数量，同时能够执行的线程数量
     * 默认情况下，核心线程会在线程池中一直存活， 即使处于闲置状态.
     * 但如果将allowCoreThreadTimeOut设置为true的话, 那么核心线程也会有超时机制，
     * 在keepAliveTime设置的时间过后， 核心线程也会被终止.
     */
    private int corePoolSize;
    /**
     * 最大线程数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
     * 最大的线程数， 包括核心线程， 也包括非核心线程，
     * 在线程数达到这个值后，新来的任务将会被阻塞.
     */
    private int maximumPoolSize;
    /**
     * 超时的时间， 闲置的非核心线程超过这个时长，讲会被销毁回收，
     * 当allowCoreThreadTimeOut为true时，这个值也作用于核心线程.
     */
    private long     keepAliveTime = 1;
    /**
     * 超时时间的时间单位.
     */
    private TimeUnit unit          = TimeUnit.HOURS;

    // 线程池
    private ThreadPoolExecutor mThreadPoolExecutor;// 线程池
    private String defaultThreadName = getClass().getSimpleName();// 线程默认名字

    private LThreadPoolMgr() {
        // 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        //虽然maximumPoolSize用不到，但是需要赋值，否则报错
        maximumPoolSize = corePoolSize;
        createThreadPoolExecutor(defaultThreadName);
    }

    public static LThreadPoolMgr getInstance() {
        if (mInstance == null) {
            synchronized (LThreadPoolMgr.class) {
                if (mInstance == null) {
                    mInstance = new LThreadPoolMgr();
                }
            }
        }
        return mInstance;
    }


    private ThreadPoolExecutor createThreadPoolExecutor(String threadNamePrefix) {
        //线程池执行者。
        //参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
        mThreadFactory = new DefaultThreadFactory(Thread.NORM_PRIORITY, threadNamePrefix);
        mThreadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,//当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                maximumPoolSize,//5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime,//表示的是maximumPoolSize当中等待任务的存活时间
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),//缓冲队列，用于存放等待任务，Linked的先进先出
                //   Executors.defaultThreadFactory(),
                mThreadFactory,//创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy()); //用来对超出maximumPoolSize的任务的处理策略
        return mThreadPoolExecutor;
    }

    /**
     * 执行任务 指定任务名称的runnable
     */
    public void execute(@NonNull Runnable runnable, String runnableName) {
        if (mThreadPoolExecutor == null) {
            createThreadPoolExecutor(defaultThreadName);
        }
        mThreadFactory.namePrefix = defaultThreadName + runnableName;
        mThreadPoolExecutor.setThreadFactory(mThreadFactory);
        mThreadPoolExecutor.execute(runnable);
    }

    /**
     * 执行任务 runnable
     */
    public void execute(Runnable runnable) {
        if (mThreadPoolExecutor == null) {
            createThreadPoolExecutor(defaultThreadName);
        }
        if (runnable != null) {
            mThreadPoolExecutor.execute(runnable);
        }
    }

    /**
     * 执行任务
     */
    public void executeCustom(@NonNull LCustomerNoParam customer, @NonNull Runnable runnable) {
        if (mThreadPoolExecutor == null) {
            createThreadPoolExecutor(defaultThreadName);
        }
        customer.accept();
        mThreadPoolExecutor.execute(runnable);

    }

    /**
     * 移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable != null) {
            mThreadPoolExecutor.remove(runnable);
        }
    }

    /**
     * 创建线程的工厂，设置线程的优先级，group，以及命名
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        /**
         * 线程池的计数
         */
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        /**
         * 线程的计数
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private ThreadGroup group;
        private String namePrefix;
        private int         threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + "-线程池数:" + POOL_NUMBER.getAndIncrement() + "-线程号-";
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            t.setPriority(threadPriority);
            return t;
        }
    }

}
