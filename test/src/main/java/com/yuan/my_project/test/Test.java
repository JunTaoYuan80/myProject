package com.yuan.my_project.test;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] ary = {5,9,2,10,4,8,21,9,4,32,53,23,16,25}; 
		quickSort(ary);
		for(int i=0;i<ary.length;i++){
			System.out.print(ary[i]+",");
		}
		
		for(;;){
			
		}
	}

	public static void quickSort(int[] arr){
	    qsort(arr, 0, arr.length-1);
	}
	private static void qsort(int[] arr, int low, int high){
	    if (low < high){
	        int pivot=partition(arr, low, high);        //�������Ϊ������
	        qsort(arr, low, pivot-1);                   //�ݹ�������������
	        qsort(arr, pivot+1, high);                  //�ݹ�������������
	    }
	}
	private static int partition(int[] arr, int low, int high){
	    int pivot = arr[low];     //�����¼
	    while (low<high){
	        while (low<high && arr[high]>=pivot) --high;
	        arr[low]=arr[high];             //����������С�ļ�¼�����
	        while (low<high && arr[low]<=pivot) ++low;
	        arr[high] = arr[low];           //����������С�ļ�¼���Ҷ�
	    }
	    //ɨ����ɣ����ᵽλ
	    arr[low] = pivot;
	    //���ص��������λ��
	    return low;
	}
}
