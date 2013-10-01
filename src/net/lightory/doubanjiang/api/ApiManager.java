package net.lightory.doubanjiang.api;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ApiManager {
    private static ApiManager instance = null;
    private final LinkedBlockingQueue<Runnable> queue;
    private final ThreadPoolExecutor threadPool;

    private ApiManager() {
        this.queue = new LinkedBlockingQueue<Runnable>();
        this.threadPool = new ThreadPoolExecutor(2, 2, 120, TimeUnit.SECONDS, this.queue);
    }

    public static ApiManager getInstance() {
        if (null == instance) {
            instance = new ApiManager();
        }
        return instance;
    }

    public void execute(BaseApi api) {
        this.threadPool.execute(api);
    }
}
