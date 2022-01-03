package org.example.algorithms.sort;

/**
 * 堆排序算法
 * 空间复杂度 1 ，时间复杂度 nlogn
 * @Author: MaiYu
 * @Date: Create in 21:12 2022/1/3
 */
public class heapSort {


    public static void MAX_HEAPIFY(int[] ans,int i,int length){

        int max=i;
        int left=2*i;
        int right=2*i+1;

        if (left<length && ans[max]<ans[left]){
            max=left;
        }
        if (right<length && ans[max]<ans[right]){
            max=right;
        }

        if (max!=i){
            exchange(ans,max,i);
            MAX_HEAPIFY(ans,max,length);
        }
    }
    public static void BUILD_MAX_HEAP(int[] a){
        int heapSize=a.length;
        for (int i=(heapSize-1)/2;i>=0;i--){
            MAX_HEAPIFY(a,i,heapSize);
        }
    }

    public static void heapSort(int[] a){
        //最大堆
        BUILD_MAX_HEAP(a);
        for (int ans:a){
            System.out.println(ans);

        }
        for (int i=a.length-1;i>0;i--){
            exchange(a,0,i);
            //重新调整
            MAX_HEAPIFY(a,0,i);
        }
    }
    public static void exchange(int[] a,int i,int j){
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    public static void main(String[] args) {
        int[] ans = new int[]{-20, 17, 8, -6, 10, -12, -18, -30};
        heapSort(ans);
        for (int an:ans){
            System.out.println(an);
        }
    }

}
