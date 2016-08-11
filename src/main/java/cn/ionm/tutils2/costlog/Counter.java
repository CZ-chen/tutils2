package cn.ionm.tutils2.costlog;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 56574 on 2016-6-13.
 */
@JsonAutoDetect
public class Counter {
    private String key;
    @JsonIgnore
    private transient ConcurrentHashMap<Long,Long> startTimes = new ConcurrentHashMap<Long,Long>();
    private AtomicLong invokCount = new AtomicLong();
    private AtomicLong totalCost = new AtomicLong();
    private long main = Long.MAX_VALUE;
    private long max = 0L;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AtomicLong getInvokCount() {
        return invokCount;
    }

    public void setInvokCount(AtomicLong invokCount) {
        this.invokCount = invokCount;
    }

    public AtomicLong getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(AtomicLong totalCost) {
        this.totalCost = totalCost;
    }

    public long getMain() {
        return main;
    }

    public void setMain(long main) {
        this.main = main;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public void begin(){
        startTimes.put(Thread.currentThread().getId(),System.currentTimeMillis());
        invokCount.incrementAndGet();
    }

    public void end(){
        Long startTime = startTimes.get(Thread.currentThread().getId());
        if(startTime!=null){
            long cost = System.currentTimeMillis()-startTime;
            if(cost>0){
                totalCost.addAndGet(cost);
                if(cost<main){
                    main = cost;
                }
                if(cost>max){
                    max = cost;
                }
            }
        }
    }
}
