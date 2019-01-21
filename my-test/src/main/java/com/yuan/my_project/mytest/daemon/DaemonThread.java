package com.yuan.my_project.mytest.daemon;

/**
 * daemon线程，当主进程消亡时，守护线程跟着消亡；non-daemon线程，当主进程消亡时，守护线程继续存在
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
