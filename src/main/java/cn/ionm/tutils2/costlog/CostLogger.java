package cn.ionm.tutils2.costlog;

import cn.ionm.tutils2.jackson.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 56574 on 2016-6-13.
 */
public class CostLogger {
    private Logger logger;
    private boolean enabled;
    private long logPeriod = 60000;
    public static final long SYS_START_TIME = System.currentTimeMillis();

    private ConcurrentHashMap<String,Counter> counters = new ConcurrentHashMap<String,Counter>();
    private Timer timer = new Timer();

    static ObjectMapper jsonMapper = new ObjectMapper();
    static{
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public CostLogger(Logger logger,long logPeriod){
        this.logger = logger;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                logger.info(CostLogger.this.toString());
                counters.clear();
            }
        },0,logPeriod);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Counter getCounter(String name){
        Counter counter = counters.get(name);
        if(counter==null){
            counter = new Counter();
            counter.setKey(name);
            counters.put(name,counter);
        }
        return counter;
    }

    public void begin(String name){
        getCounter(name).begin();
    }

    public void end(String name){
        getCounter(name).end();
    }

    public String toString(){
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(counters.keySet());
        Collections.sort(keyList);
        List<Counter> counters = new ArrayList<Counter>();
        for(String key:keyList){
            counters.add(getCounter(key));
        }
        return JsonUtils.toJson(counters,jsonMapper);
    }
}
