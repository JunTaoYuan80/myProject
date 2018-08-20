package com.yuan.my_project.mytest;

/**
 * @author yuanjuntao
 * @date 2018/5/1 18:19
 */
public class Test2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //t(1);
        //t2();

        int[] a =  {1,3,9,12,14};
        System.out.println(binary(a,0,a.length-1,23));
    }



    public static int binary(int[] a,int min,int max, int b){
        if(a==null || a.length == 0 || min<0 || max>=a.length-1 || max<min){
            return -1;
        }
        int mid = (min+max)/2;
        if(a[mid]>b){
            return binary(a,0,mid-1,b);
        }else if(a[mid]<b){
            return binary(a,mid+1,a.length-1,b);
        }else{
            return mid;
        }
    }
}
