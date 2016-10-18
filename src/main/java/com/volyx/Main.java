package com.volyx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by volyx on 18.10.16.
 */
public class Main {

    public static void main(String[] args) {
        int corePoolSize = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newScheduledThreadPool(corePoolSize + 1);
//        ExecutorService executorService = Executors.newFixedThreadPool(corePoolSize + 1);

        for (int i = 0; i < corePoolSize; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    while (true) {
                        System.out.println("Sleeping ...");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Throwing ... ");
                        throw new RuntimeException("bad ass!");
                    }
                }
            });
        }
    }
}
