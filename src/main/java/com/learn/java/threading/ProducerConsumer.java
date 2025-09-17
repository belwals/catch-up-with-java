package com.learn.java.threading;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ProducerConsumer {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new java.util.concurrent.LinkedBlockingQueue<>(10);
        AtomicBoolean isStopped = new AtomicBoolean(false);
        Thread producerThread = new Thread(new Producer(queue, isStopped));
        Thread consumerThread = new Thread(new Consumer(queue, isStopped));

        producerThread.start();
        consumerThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            isStopped.set(true);
        }

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
    }
}

class Producer implements Runnable {

    private final BlockingQueue<String> queue;
    private final AtomicBoolean isStopped;
    private static final Logger logger = Logger.getLogger(Producer.class.getName());

    public Producer(BlockingQueue<String> queue, AtomicBoolean isStopped) {
        this.queue = queue;
        this.isStopped = isStopped;
    }

    public void run() {
        while (!this.isStopped.get()) {

            UUID message = UUID.randomUUID();
            try {
                queue.put(message.toString());
                // Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } 
        }
        logger.info("Producer Stopped");
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private final AtomicBoolean isStopped;
    private static final Logger logger = Logger.getLogger(Consumer.class.getName());

    public Consumer(BlockingQueue<String> queue, AtomicBoolean isStopped) {
        this.queue = queue;
        this.isStopped = isStopped;
    }

    public void run() {
        while (!(this.isStopped.get() && queue.isEmpty())) {   
            try {
                String message = queue.poll(300, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (message != null) {
                    logger.info("Consumed: " + message);
                }
            } catch (InterruptedException e) {
                logger.info("Consumer interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
        logger.info("Consumer finished");
    }
}
