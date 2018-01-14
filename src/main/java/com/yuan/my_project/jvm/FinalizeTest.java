package com.yuan.my_project.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * object类的finalize方法测试
 * @author yuanjuntao
 */
public class FinalizeTest {


    public static void main(String[] args){

        List list = new ArrayList();
        for(;;){
            byte[] b = new byte[1024*100];
            list.add(b);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void finalize(){
        System.out.println("i am test finalize");
    }
}
