package com.volyx;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by volyx on 19.10.16.
 */
public class ScheduledMain {

    public static void main(String[] args) throws InterruptedException {

        final Object mutex = new Object();
        final Semaphore semaphore = new Semaphore(3);
//        final Lock lock = new ReentrantLock();


        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3) {

            protected void beforeExecute(Thread t, Runnable r) {

            }

            protected void afterExecute(Runnable r, Throwable t) {
//                synchronized (mutex) {
//                    mutex.notify();
//                }
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
//                lock.unlock();
            }

        };

        Runnable command = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + ""+ new Date());
            }
        };

        for (int i = 0 ; i < 3; i++) {
            Thread thread = new Thread(command);
            thread.setName("t-" + i);
            executor.submit(thread);
        }

        while(true) {
//            synchronized (mutex) {
//                mutex.wait();
//            }
//            Thread.sleep(10000L);
            semaphore.acquire();
//            lock.lock();
            executor.submit(command);
        }

    }
}
