package com.nearinfinity.examples.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListenableFuturesTest {

    private ListeningExecutorService _executorService;

    @Before
    public void setUp() {
        ExecutorService delegate = Executors.newCachedThreadPool();
        _executorService = MoreExecutors.listeningDecorator(delegate);
    }

    @Test
    public void testListenableFuture_UsingSimpleListener() throws Exception {
        final CountDownLatch completionSignal = new CountDownLatch(1);
        final boolean[] wasCalled = new boolean[]{false};

        ListenableFuture<WorkResult> future = _executorService.submit(new Worker("Worker1"));
        future.addListener(new Runnable() {
            @Override
            public void run() {
                wasCalled[0] = true;
                completionSignal.countDown();
            }
        }, _executorService);

        completionSignal.await();
        assertTrue(wasCalled[0]);
    }

    @Test
    public void testListenableFuture_UsingFutureCallback() throws Exception {
        ImmutableList<Worker> workers = ImmutableList.of(
                new SuccessfulWorker("Worker1"),
                new SuccessfulWorker("Worker2"),
                new FailingWorker("Worker3"),
                new SuccessfulWorker("Worker4"),
                new SuccessfulWorker("Worker5"));

        final CountDownLatch completionSignal = new CountDownLatch(workers.size());
        final List<Worker> successfulWorkers = Collections.synchronizedList(new ArrayList<Worker>());
        final List<Worker> failedWorkers = Collections.synchronizedList(new ArrayList<Worker>());

        // TODO Find out what following commented code is, and remove or implement it.
        /*
        List<Future<WorkResult>> futures = _executorService.invokeAll(workers);
        for (Future<WorkResult> future : futures) {
            Futures.addCallback((ListenableFuture<WorkResult>) future, new FutureCallback<WorkResult>() {
                @Override
                public void onSuccess(WorkResult result) {
                    completionSignal.countDown();
                    successfulWorkers.add(result.worker);
                    System.out.printf("%s: %d seconds\n", result.worker.getName(), result.timeToComplete);
                }

                @Override
                public void onFailure(Throwable t) {
                    completionSignal.countDown();
                    WorkerException ex = (WorkerException) t;
                    failedWorkers.add(ex.worker);
                    System.out.printf("%s: %s\n", ex.worker.getName(), ex.getMessage());
                }
            }, _executorService);
        }
        */

        for (Worker worker : workers) {
            ListenableFuture<WorkResult> future = _executorService.submit(worker);
            Futures.addCallback(future, new FutureCallback<WorkResult>() {
                @Override
                public void onSuccess(WorkResult result) {
                    completionSignal.countDown();
                    successfulWorkers.add(result.worker);
                    System.out.printf("%s: %d seconds\n", result.worker.getName(), result.timeToComplete);
                }

                @Override
                public void onFailure(Throwable t) {
                    completionSignal.countDown();
                    WorkerException ex = (WorkerException) t;
                    failedWorkers.add(ex.worker);
                    System.out.printf("%s: %s\n", ex.worker.getName(), ex.getMessage());
                }
            }, _executorService);
        }

        completionSignal.await();

        assertThat(successfulWorkers.size(), is(4));
        assertTrue(successfulWorkers.contains(workers.get(0)));
        assertTrue(successfulWorkers.contains(workers.get(1)));
        assertFalse(successfulWorkers.contains(workers.get(2)));
        assertTrue(successfulWorkers.contains(workers.get(3)));
        assertTrue(successfulWorkers.contains(workers.get(4)));

        assertThat(failedWorkers.size(), is(1));
        assertTrue(failedWorkers.contains(workers.get(2)));
    }

    public class Worker implements Callable<WorkResult> {

        private final String _name;
        private final boolean _shouldFail;
        private final Random _random;

        public Worker(String name) {
            this(name, false);
        }

        public Worker(String name, boolean shouldFail) {
            _name = name;
            _shouldFail = shouldFail;
            _random = new SecureRandom();
        }

        @Override
        public WorkResult call() throws Exception {
            int workTimeSeconds = 2 + _random.nextInt(10);  // 2-12 seconds
            Uninterruptibles.sleepUninterruptibly(workTimeSeconds, TimeUnit.SECONDS);
            if (_shouldFail) {
                throw new WorkerException(this, workTimeSeconds);
            }
            return new WorkResult(this, workTimeSeconds);
        }

        public String getName() {
            return _name;
        }
    }

    public class SuccessfulWorker extends Worker {
        public SuccessfulWorker(String name) {
            super(name);
        }
    }

    public class FailingWorker extends Worker {
        public FailingWorker(String name) {
            super(name, true);
        }
    }

    public class WorkResult {
        public final Worker worker;
        public final int timeToComplete;

        public WorkResult(Worker worker, int timeToComplete) {
            this.worker = worker;
            this.timeToComplete = timeToComplete;
        }
    }

    public class WorkerException extends Exception {
        public final Worker worker;
        public final int workTime;

        public WorkerException(Worker worker, int workTime) {
            super(String.format("Worker [%s] threw exception after %d seconds", worker.getName(), workTime));
            this.worker = worker;
            this.workTime = workTime;
        }
    }
}
