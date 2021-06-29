package com.com.sumauto.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

public class Code {
    /**
     * 删除排序数组中的重复项
     */
    @Test
    public void testRemoveDuplicates() {
        int[] input0 = new int[]{};
        int[] input1 = new int[]{1};
        int[] input2 = new int[]{1, 1};
        int[] input3 = new int[]{1, 2};
        int[] input4 = new int[]{1, 1, 2, 3, 4, 4, 5, 5, 5, 6, 7};
        int[] input5 = new int[]{1, 1, 2, 3, 4, 4, 5, 5, 5, 6, 7, 7};
        System.out.println(removeDuplicates(input0));
        System.out.println(removeDuplicates(input1));
        System.out.println(removeDuplicates(input2));
        System.out.println(removeDuplicates(input3));
        System.out.println(removeDuplicates(input4));
        System.out.println(removeDuplicates(input5));
    }

    @Test
    public void testStock() {
        int[] prices1 = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(getStockProfit(prices1));
        System.out.println(getStockProfit(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testMoveArray() {
        moveArrayTests(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        moveArrayTests(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    private void moveArrayTests(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int[] newArray = new int[nums.length];
            System.arraycopy(nums, 0, newArray, 0, newArray.length);
            moveAdapter(newArray, i);
        }
    }

    private void moveAdapter(int[] nums, int k) {
        System.out.println("start:" + Arrays.toString(nums) + " " + k);
        moveArray2(nums, k);
        System.out.println("result:" + Arrays.toString(nums) + " " + k);

    }

    /**
     * 1,2,3,4
     */
    public void moveArray2(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
    }
    private void moveArray(int[] nums, int k) {
        int n = nums.length;
        int realMove = k % n;
        if (realMove == 0) {
            return;
        }
        int moveIndex;
        int[] tempArray = new int[n];
        for (int i = 0; i < n; i++) {
            moveIndex = i + realMove;
            if (moveIndex >= n) {
                moveIndex = moveIndex - n;
            }
            tempArray[moveIndex] = nums[i];
        }
        System.arraycopy(tempArray, 0, nums, 0, n);
    }

    private int getStockProfit(int[] prices) {
        int profit = 0;
        int day = 1;
        int buy = 0;
        while (day < prices.length) {
            if (prices[buy] > prices[day]) {
                buy = day;
            } else if (prices[buy] < prices[day]) {
                profit += prices[day] - prices[buy];
                buy = day;
            }
            day++;
        }

        return profit;
    }

    private int removeDuplicates(int[] input) {
        int n = input.length;
        if (n <= 1) {
            return n;
        }
        int fast = 0, slow = 0;
        System.out.println(Arrays.toString(input));
        while (fast < n - 1) {
            if (input[fast] != input[fast + 1]) {
                ++slow;
                input[slow] = input[fast + 1];
            }
            ++fast;
        }
        System.out.println(Arrays.toString(input));

        return slow + 1;

    }
}
