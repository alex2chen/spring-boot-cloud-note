package com.github.springkit;

import org.junit.Test;

/**
 * Created by YT on 2018/5/23.
 */
public class AwatiNotify {
    private boolean available = true;

    @Test
    public void go_simpel() throws InterruptedException {
        System.out.println("test");
        put(1);
        System.out.println("end");

//        new put(q);
//        new get(q);
    }

    public synchronized void put(int value) {
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}

class put implements Runnable {
    Q q;

    public put(Q q) {
        this.q = q;
        new Thread(this, "Put").start();
    }


    public void run() {
        int i = 0;
        while (true) {
            q.put(i++);
        }
    }
}

class get implements Runnable {
    Q q;


    public get(Q q) {
        this.q = q;
        new Thread(this, "Get").start();
    }


    public void run() {
        while (true) {
            q.get();
        }
    }
}

class Q {
    private int n;
    private boolean isWait = false;


    synchronized void put(int n) {
        if (isWait) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.n = n;
        System.out.println("put=" + n);
        isWait = true;
        notify();
    }

    synchronized void get() {
        if (!isWait) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("get=" + n);
        isWait = false;
        notify();
    }
}

