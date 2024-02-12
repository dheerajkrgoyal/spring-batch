package com.dheeraj.springbatchpractice.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class CustomerPartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = 1;
        int max = 1000;
        int targetSize = (max-min)/gridSize+1;
        int number = 0;
        int start = min;
        int end = start+targetSize-1;
        Map<String, ExecutionContext> map = new HashMap<>();
        while(start<=max){
            ExecutionContext context = new ExecutionContext();
            map.put("partition" + number, context);
            if(end>=max){
                end = max;
            }

            context.putInt("minValue", start);
            context.putInt("maxValue", end);
            start+=targetSize;
            end+=targetSize;
            number++;
        }
        System.out.println("Partition Result: " + map);
        return map;
    }
}
