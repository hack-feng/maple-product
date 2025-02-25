package com.maple.thread;

/**
 * @author zhangfuzeng
 * @date 2025/2/25
 */
public class ThreadVisibilityTest {

    // 使用 volatile 关键字修饰变量
    private static boolean flag = true;

    public static void main(String[] args) {
        // 线程 1：修改 flag 的值
        Thread writer = new Thread(() -> {
            System.out.println("Writer thread is sleeping...");
            try {
                // 模拟一些操作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Writer thread is setting flag to true...");
            flag = false;
            System.out.println("Writer thread has set flag to true.");
        });

        // 线程 2：读取 flag 的值
        Thread reader = new Thread(() -> {
            while (flag) {
                // 循环等待 flag 变为 true
                
            }
            System.out.println("Reader thread has detected that flag is true.");
        });

        // 启动线程
        reader.start();
        writer.start();

       
    }
}
