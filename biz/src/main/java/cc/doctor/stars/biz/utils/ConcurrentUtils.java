package cc.doctor.stars.biz.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * 并发工具
 *
 * @author chenzhi03
 */
@Slf4j
public class ConcurrentUtils {
    private static final ExecutorService parallelPool = Executors.newFixedThreadPool(4);
    private static final ExecutorService cachePool = Executors.newCachedThreadPool();

    public static void newParallelPool(int nThreads, Collection<Runnable> runnables) throws InterruptedException {
        ExecutorService parallelPool = Executors.newFixedThreadPool(nThreads);
        parallel(parallelPool, runnables.toArray(new Runnable[0]));
        parallelPool.shutdown();
    }

    public static void newParallelPool(int nThreads, Runnable... runnables) throws InterruptedException {
        ExecutorService parallelPool = Executors.newFixedThreadPool(nThreads);
        parallel(parallelPool, runnables);
        parallelPool.shutdown();
    }

    public static void parallel(Runnable... runnables) throws InterruptedException {
        parallel(parallelPool, runnables);
    }

    public static void parallel(ExecutorService parallelPool, Runnable... runnables) throws InterruptedException {
        if (runnables.length == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(runnables.length);
        for (Runnable runnable : runnables) {
            parallelPool.submit(() -> {
                try {
                    runnable.run();
                } catch (Exception e) {
                    log.error("", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }

    public static void schedule(String name, int delay, int period, Runnable runnable) {
        schedule(name, delay, period, TimeUnit.SECONDS, runnable);
    }

    public static void schedule(String name, int delay, int period, TimeUnit timeUnit, Runnable runnable) {
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setName(name);
            return t;
        }).scheduleAtFixedRate(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("", e);
            }
        }, delay, period, timeUnit);
    }

    public static void executeFast(Runnable runnable) {
        cachePool.submit(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }

    @Getter
    public static class FixedThreadPool {
        private final Semaphore semaphore;

        private final ExecutorService executorService;

        private final String name;

        private final int initPermits;

        public FixedThreadPool(int permits, String name) {
            this.initPermits = permits;
            this.name = name;
            semaphore = new Semaphore(permits);
            executorService = new ThreadPoolExecutor(permits, permits, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50), r -> {
                Thread thread = new Thread(r);
                thread.setName(name);
                return thread;
            });
        }

        public void acquireDo(Runnable... runnables) {
            for (Runnable runnable : runnables) {
                try {
                    semaphore.acquire();
                    executorService.submit(() -> {
                        try {
                            runnable.run();
                        } catch (Exception e) {
                            log.info("", e);
                        } finally {
                            semaphore.release();
                        }
                    });
                } catch (InterruptedException e) {
                    //
                }
            }
        }
    }
}
