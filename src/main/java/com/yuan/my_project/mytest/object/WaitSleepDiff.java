package com.yuan.my_project.mytest.object;

/**
 * wait和sleep方法的区别
 * wait会释放Monitor锁，sleep不会释放monitor锁
 * http://blog.csdn.net/u012050154/article/details/50903326
 *
 * https://www.zhihu.com/question/23328075/answer/24228413
 * 首先，要记住这个差别，“sleep是Thread类的方法,wait是Object类中定义的方法”。尽管这两个方法都会影响线程的执行行为，但是本质上是有区别的。
 * Thread.sleep不会导致锁行为的改变，如果当前线程是拥有锁的，那么Thread.sleep不会让线程释放锁。如果能够帮助你记忆的话，可以简单认为和锁相关的方法都定义在Object类中，因此调用Thread.sleep是不会影响锁的相关行为。
 * Thread.sleep和Object.wait都会暂停当前的线程，对于CPU资源来说，不管是哪种方式暂停的线程，都表示它暂时不再需要CPU的执行时间。OS会将执行时间分配给其它线程。区别是，调用wait后，需要别的线程执行notify/notifyAll才能够重新获得CPU执行时间。
 * 线程的状态参考 Thread.State的定义。新创建的但是没有执行（还没有调用start())的线程处于“就绪”，或者说Thread.State.NEW状态。
 * Thread.State.BLOCKED（阻塞）表示线程正在获取锁时，因为锁不能获取到而被迫暂停执行下面的指令，一直等到这个锁被别的线程释放。BLOCKED状态下线程，OS调度机制需要决定下一个能够获取锁的线程是哪个，这种情况下，就是产生锁的争用，无论如何这都是很耗时的操作。
 * @author yuanjt
 * @data 2018-02-01
 */
public class WaitSleepDiff {

    public static void main(String[] args){

        new Thread(new Thread1()).start();
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        new Thread(new Thread2()).start();


    }



    private static class Thread1 implements Runnable{
        @Override
        public void run() {
            //由于 Thread1和下面Thread2内部run方法要用同一对象作为监视器，如果用this则Thread1和Threa2的this不是同一对象
            //所以用MultiThread.class这个字节码对象，当前虚拟机里引用这个变量时指向的都是同一个对象
            synchronized(WaitSleepDiff.class){
                System.out.println("enter thread1 ...");
                System.out.println("thread1 is waiting");

                try{
                    //释放锁有两种方式：(1)程序自然离开监视器的范围，即离开synchronized关键字管辖的代码范围
                    //(2)在synchronized关键字管辖的代码内部调用监视器对象的wait()方法。这里使用wait方法
                    WaitSleepDiff.class.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("thread1 is going on ...");
                System.out.println("thread1 is being over!");
            }
        }

    }

    private static class Thread2 implements Runnable{
        @Override
        public void run() {
            //notify方法并不释放锁，即使thread2调用了下面的sleep方法休息10ms，但thread1仍然不会执行
            //因为thread2没有释放锁，所以Thread1得不到锁而无法执行
            synchronized(WaitSleepDiff.class){
                System.out.println("enter thread2 ...");
                System.out.println("thread2 notify other thread can release wait status ...");
                WaitSleepDiff.class.notify();
                System.out.println("thread2 is sleeping ten millisecond ...");

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("thread2 is going on ...");
                System.out.println("thread2 is being over!");
            }
        }
    }
}
