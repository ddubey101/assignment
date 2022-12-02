package com.cs.service;

import com.cs.dao.Event;
import com.cs.dao.EventDao;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FileReader implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    private String filePath;
    private ConcurrentHashMap<String, Long> hashMap = new ConcurrentHashMap();
    private Set<String> set = new HashSet();
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void run() {
        try {
            readFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readFile (String filePath) throws Exception{
        String currLine;
        long start = System.currentTimeMillis();
        BufferedReader buf = new BufferedReader(new java.io.FileReader(filePath));
        while ((currLine = buf.readLine()) != null) {
            JSONParser jp = new JSONParser();
            JSONObject jsonObject = (JSONObject) jp.parse(currLine);
            String id = (String)jsonObject.get("id");
            String type = (String)jsonObject.get("type");
            String host = (String)jsonObject.get("host");
            Long timestamp = Long.parseLong((String)jsonObject.get("timestamp"));
            boolean alert = false;
            if (hashMap.containsKey(id)) {
                long ts1 = hashMap.get(id);
                long ts2 = Math.abs(timestamp - ts1);
                if (ts2 > 4) {
                    // TODO saving found event details to file based HSQLDB
                    alert = true;
                    //EventDao.saveToFile(new Event(id, ts2, type, host, alert));
                    set.add(id);
                }
                // TODO saving event details to DB
                boolean isAlert = alert;
                Runnable auditStoreProvisionTask = () -> {
                    EventDao.saveToDb(new Event(id, ts2, type, host, isAlert));
                };
                CompletableFuture.runAsync(auditStoreProvisionTask);

            } else {
                hashMap.put(id,timestamp);
            }
        }
        LOGGER.info("Time taken to save events in db:" + (System.currentTimeMillis() - start));
        LOGGER.info("Event IDs taking longer time:"+ set.toString());
    }
}
