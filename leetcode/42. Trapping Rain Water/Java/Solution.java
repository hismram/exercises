/*
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

n == height.length
1 <= n <= 2 * 10**4
0 <= height[i] <= 10**5
*/

import java.util.ArrayList;

// А вот это получилось неплохо, судя по замерам

class Solution {
    public int trap(int[] height) {
        int summary = 0;
        ArrayList<Integer> peacks = getMaxIdx(height);
        
        if (peacks.size() > 0) {
            //В первую очередь считаем крайние зоны
            summary += calcBlock(0, peacks.get(0), 1, height);
            summary += calcBlock(height.length - 1, peacks.get(peacks.size() - 1), -1, height);
            
            // если вершин несколько то считаеем между ними
            if (peacks.size() > 1) {
                for (int i = 0; i < peacks.size() - 1; i++) {
                    summary += calcBlock(peacks.get(i), peacks.get(i + 1), 1, height);
                }
            }
        }
        
        return summary;
    }
    
    // Находит самую высокую вершину или вершины к которой-ым мы будет считать
    static ArrayList<Integer> getMaxIdx(int[] height) {
        int max = 0;
        ArrayList<Integer> idx = new ArrayList<Integer>();
        for (int i = 0; i < height.length; i++) {
            if (height[i] > max) {
                max = height[i];
                idx.clear();
                idx.add(i);
            } else if(height[i] == max) {
                idx.add(i);
            }
        }
        
        return idx;
    }
    
    //Метод для подсчета воды в отдельных блоках
    static int calcBlock(int start, int stop, int step, int[] height) {
        int maxPeack = height[start];
        int buffer = 0;
        int summary = 0;
        while (start != stop) {
            if (maxPeack <= height[start]) {
                maxPeack = height[start];
                summary += buffer;
                buffer = 0;
            } else {
                buffer += maxPeack - height[start];
            }
            
            start += step;
        }
        
        summary += buffer;
        
        return summary;
    }
}