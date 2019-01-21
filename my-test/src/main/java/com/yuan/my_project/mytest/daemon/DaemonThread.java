package com.yuan.my_project.mytest.daemon;

/**
 * daemon线程，当主进程消亡时，守护线程跟着消亡；non-daemon线程，当主进程消亡时，守护线程继续存在
 * <p>
 * Daemon thread is a low priority thread (in context of JVM) that runs in background to perform tasks such as garbage collection (gc) etc.,
 * they do not prevent the JVM from exiting (even if the daemon thread itself is running) when all the user threads (non-daemon threads) finish their execution.
 * JVM terminates itself when all user threads (non-daemon threads) finish their execution, JVM does not care whether Daemon thread is running or not,
 * if JVM finds running daemon thread (upon completion of user threads), it terminates the thread and after that shutdown itself.
 *
 * @author yuanjuntao
 * @date 2019/1/21 20:53
 */
public class DaemonThread {

    public static void main(String[] args) {
        new WorkerThread().start();
        try {
            Thread.sleep(7500);
        } catch (InterruptedException e) {
            // handle here exception
        }

        System.out.println("Main Thread ending");

    }

    static class WorkerThread extends Thread {

        public WorkerThread() {
            setDaemon(false);
        }

        public void run() {
            int count = 0;

            while (true) {
                System.out.println("Hello from Worker " + count++);
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // handle exception here
                }
            }
        }

    }
}
