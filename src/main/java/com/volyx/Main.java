package com.volyx;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by volyx on 18.10.16.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int corePoolSize = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newScheduledThreadPool(corePoolSize + 1);
//        ExecutorService executorService = Executors.newFixedThreadPool(corePoolSize + 1);
            final Runnable command = new Runnable() {
                public void run() {
                    while (true) {
                        System.out.println("Sleeping ...");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Throwing ... ");
                        throw new RuntimeException("bad ass!" + new Date());
                    }
                }
            };


        Runnable commandWithException = new Runnable() {
            public void run() {
                try {
                    command.run();
                } catch (Throwable t) {
                    System.out.println("shit");
                    t.printStackTrace();
                }
            }
        };

        for (int i = 0; i < corePoolSize; i++) {
            executorService.execute(commandWithException);
        }

        Thread.sleep(10000L);
        while (true) {
            Thread.sleep(5000L);
            executorService.execute(commandWithException);
        }

    }
}
