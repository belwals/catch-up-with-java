package com.learn.java.threading;

class MyRunnable implements Runnable {

    private int threadId;
    private int maxCounter;
    private static final Object lock = new Object();

    private static int currentCounter = 1;

    public MyRunnable(int threadId, int maxCounter) {
        this.threadId = threadId;
        this.maxCounter = maxCounter;
    }

    public void run() {
        while (currentCounter <= maxCounter) {
            synchronized (lock) {
                if (currentCounter % 2 == threadId) {
                    if (currentCounter >= maxCounter) {
                        break;
                    }
                    System.out.println(this.threadId + " " + currentCounter);
                    currentCounter++;
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

public class Concurrency {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable(0, 20);
        MyRunnable myRunnable1 = new MyRunnable(1, 20);

        Thread t1= new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable1);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
}
