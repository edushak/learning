package com.learning.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class BulkVirtualThreads {

    public static void main(String[] args) {
        // Auto-closeable executor automatically orchestrates thread termination at the end of the block
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    // Simulate a blocking network or database operation
                    Thread.sleep(100);
                    System.out.println("Finished task " + i + " on " + Thread.currentThread());
                    return i;
                });
            });

        } // The try-with-resources statement implicitly triggers executor.close(), waiting for all 10,000 tasks

        System.out.println("All tasks finished running safely.");
    }
}
