package org.example.algorithms.sort;

/**
 * @Author: MaiYu
 * @Date: Create in 22:30 2022/1/4
 */
public class quickSort {

    public static void main(String[] args) {
        int[] ans = new int[]{-20, 17, 8, -6, 10, -12, -18, -30};
        mathQuickSort(ans,0,ans.length-1);
        for (int an:ans){
            System.out.println(an);
        }
    }

    public static void mathQuickSort(int[] ans,int p,int q){
        if (q>p){
            int r=quick(ans,p,q);
            mathQuickSort(ans,p,r-1);
            mathQuickSort(ans,r+1,q);
        }
    }

    public static int quick(int[] ans,int p,int q){
        int x=ans[q];
        int j=p-1;
        for (int i=p;i<=q-1;i++){
            if (ans[i]<=x){
                j+=1;
                exchange(ans,i,j);
            }
        }
        exchange(ans,q,j+1);
        return j+1;
    }

    public static void exchange(int[] a,int i,int j){
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }
}
