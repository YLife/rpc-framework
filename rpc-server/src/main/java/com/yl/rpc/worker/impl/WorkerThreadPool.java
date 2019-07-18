package com.yl.rpc.worker.impl;

import com.yl.rpc.worker.RpcThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkerThreadPool implements RpcThreadPool {

    private int nThreads;// 线程池大小
    private int queneSize;// 缓冲队列大小
    private Executor executor;// 线程池

    // 默认创建当前核心数*2大小的线程池
    public WorkerThreadPool() {
        nThreads = Runtime.getRuntime().availableProcessors() << 1;
        queneSize = 10;// 此处默认值没想好，后续看看去多大更科学
    }

    // 创建自定义大小的线程池
    public WorkerThreadPool(int nThreads, int queneSize) {
        this.nThreads = nThreads;
        this.queneSize = queneSize;
    }

    @Override
    public Executor getExecutor() {
        executor = new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(queneSize));
        return executor;
    }

}
