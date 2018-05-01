package com.yuan.my_project.jvm;

/**
 * 定位消耗cpu最多的方法
 */
public class ConsumerMaxCPU {

    public static void main(String[] args){

        for(int i=0;i<10;i++){
            new Thread(){
                public void run(){
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        Thread t = new Thread(()->{
            int i=0;
            while(true){
                i = (i++)/100;
            }
        });

        t.setName("Busiest Thread");
        t.start();
    }
}
