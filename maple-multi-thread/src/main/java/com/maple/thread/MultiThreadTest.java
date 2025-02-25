package com.maple.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangfuzeng
 * @date 2025/2/25
 */
public class MultiThreadTest {

    public static void threadA(int i) {
        System.out.println("线程A执行" + i);
    }

    public static void threadB(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程B执行" + i);
    }

    public static void threadC(int i) {
        System.out.println("线程C执行" + i);
    }

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            10,
            20,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(50),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        int n = 500;
        for (int i = 0; i < n; i++) {
            int finalI = i;
            executor.submit(() -> {
                threadA(finalI);
            });
            executor.submit(() -> {
                threadB(finalI);
            });
            executor.submit(() -> {
                threadC(finalI);
            });

        }
        System.out.println("----------------------------");
        executor.shutdown();
    }


//    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//    public static void main(String[] args) {
//        int n = 10;
//        for (int i = 0; i < n; i++) {
//            executorService.submit(MultiThreadTest::threadA);
//            executorService.submit(MultiThreadTest::threadB);
//            executorService.submit(MultiThreadTest::threadC);
//        }
//        executorService.shutdown();
//    }


//    public static void main(String[] args) throws InterruptedException {
//
//        int n = 10;
//        for (int i = 0; i < n; i++) {
//            Thread threadA = new Thread(() -> threadA());
//
//            Thread threadB = new Thread(() -> threadB());
//
//            Thread threadC = new Thread(() -> threadC());
//
//            threadA.start();
//            threadA.join();
//            threadB.start();
//            threadB.join();
//            threadC.start();
//            threadB.join();
//        }
//
//    }
}
