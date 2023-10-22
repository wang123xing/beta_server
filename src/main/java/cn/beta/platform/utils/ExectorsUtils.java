package cn.beta.platform.utils;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工作类
 */
public class ExectorsUtils {
    private ExecutorService executorService;
    public ExectorsUtils(String threadNamePrefix) {
        executorService = new ThreadPoolExecutor(1,2,1, TimeUnit.DAYS,new ArrayBlockingQueue<>(20),
                new CustomizableThreadFactory(threadNamePrefix));
    }
    public void executeJob(Runnable runnable){
        executorService.execute(runnable);
    }
}
