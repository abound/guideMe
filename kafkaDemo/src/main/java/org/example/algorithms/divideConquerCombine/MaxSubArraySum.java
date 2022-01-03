package org.example.algorithms.divideConquerCombine;

/**
 * 使用分支法求出最小子数组和
 *
 * @Author: MaiYu
 * @Date: Create in 19:20 2022/1/3
 */
public class MaxSubArraySum {

    public static void main(String[] args) {
        int[] ans = new int[]{-20, 17, 8, -6, 10, -12, -18, -30};
        Result result = FIND_MAXIMUM_SUBARRAY(ans, 0, ans.length - 1);
        System.out.println("最大子数组是从第"+(result.low+1)+"个到"+(result.high+1)+"个，和为："+result.sum);


    }

public static class Result {
    public int low;
    public int high;
    public int sum;

    public Result(int low, int high, int sum) {
        this.low = low;
        this.high = high;
        this.sum = sum;
    }

}

    public static Result FIND_MAX_CROSSING_SUBARRAY(int[] ans, int low, int mid, int high) {
        int left_sum = Integer.MIN_VALUE;
        int sum = 0;
        int max_left = 0;
        for (int i = mid; i > low; i--) {
            sum += ans[i];
            if (sum > left_sum) {
                left_sum = sum;
                max_left = i;
            }
        }
        sum = 0;
        int max_right = 0;
        int right_sum = Integer.MIN_VALUE;
        for (int j = mid + 1; j < high; j++) {
            sum += ans[j];
            if (sum > right_sum) {
                right_sum = sum;
                max_right = j;
            }
        }

        return new Result(max_left, max_right, left_sum + right_sum);
    }

    public static Result FIND_MAXIMUM_SUBARRAY(int[] ans, int low, int high) {

        if (low == high) {
            return new Result(low, high, ans[low]);
        } else {
            int mid = (low + high) / 2;
            Result left_result = FIND_MAXIMUM_SUBARRAY(ans, low, mid);
            Result right_result = FIND_MAXIMUM_SUBARRAY(ans, mid + 1, high);
            Result crossing_result = FIND_MAX_CROSSING_SUBARRAY(ans, low, mid, high);
            if (left_result.sum >= right_result.sum && left_result.sum >= crossing_result.sum) {
                return left_result;
            } else if (right_result.sum >= left_result.sum && right_result.sum >= crossing_result.sum) {
                return right_result;
            } else {
                return crossing_result;
            }
        }
    }

}
