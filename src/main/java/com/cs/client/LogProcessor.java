package com.cs.client;

import com.cs.service.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessor.class);

    public static void main(String[] args) throws Exception{
        FileReader fileReader = new FileReader();
        fileReader.setFilePath("\\Assignment\\logfile.txt");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(fileReader);
        executorService.shutdown();
    }
}
