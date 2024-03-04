/*
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

The overall run time complexity should be O(log (m+n)).

Constraints:
nums1.length == m
nums2.length == n
0 <= m <= 1000
0 <= n <= 1000
1 <= m + n <= 2000
-10**6 <= nums1[i], nums2[i] <= 10**6
*/

function findMedianSortedArrays(nums1: number[], nums2: number[]): number {
    let full: number[] = [...nums1, ...nums2].sort((a, b) => {return a - b});

    if (full.length % 2 === 0) {
        return (full[(full.length / 2) - 1] + full[(full.length / 2)]) / 2;
    } else {
        return full[Math.floor(full.length / 2)];
    }    
};
