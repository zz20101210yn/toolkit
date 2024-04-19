package com.paic.jrkj.tk.samples.tools.work;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.paic.jrkj.tk.tools.work.WorkManager;

public class PoolSample {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            WorkManager.getWorkManager("default").startWork(new Runnable() {
                @Override
                public void run() {
                    System.out.println("nice");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
             try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        }
//		int corePoolSize = 5;
//        int maximumPoolSize = 10;
//        long keepAliveTime = 15000L;
//        int queCapacity = -1;
//        BlockingQueue<Runnable> queue = queCapacity <= 0 ?
//                new LinkedBlockingDeque<Runnable>() :
//                new LinkedBlockingDeque<Runnable>(queCapacity);
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
//                TimeUnit.MILLISECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
//        pool.execute(new Runnable() {
//
//			@Override
//			public void run() {
//
//			}
//		});
    }
}
