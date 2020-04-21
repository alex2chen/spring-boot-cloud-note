package com.github.springkit.moco.util;

public class Waiter {
    private int interval = 500;
    private int max = 10000;

    public Waiter() {

    }

    public Waiter(int interval, int max) {
        this.interval = interval;
        this.max = max;
    }

    public void until(Condition condition) {
        until(condition, null);
    }

    public void until(Condition condition, TimeoutCallback onTimeout) {
        boolean passedCondition = false;
        int elapsedMillis = 0;

        do {
            if (condition.check()) {
                passedCondition = true;
            } else {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            elapsedMillis += interval;
        } while (!passedCondition && elapsedMillis < max);

        if (!passedCondition) {
            if (onTimeout == null) {
                throw new TimeoutException();
            } else {
                onTimeout.execute();
            }
        }
    }

    public interface Condition {
        /**
         * Check whether the condition passes.
         * @return true for pass or false for fail
         */
        boolean check();
    }

    public interface TimeoutCallback {
        void execute();
    }

    private class TimeoutException extends RuntimeException {
    }
}

