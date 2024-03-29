package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomConsumerSaveMetric {

    private static String TOPIC_NAME = "custom-computer-metric";
    private static String GROUP_ID = "custom-metric-consumers";
    private static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static int CONSUMER_COUNT = 1;
    private static List<CustomConsumerWorker> workerThreads = new ArrayList<>();

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < CONSUMER_COUNT; i++){
            CustomConsumerWorker worker = new CustomConsumerWorker(configs, TOPIC_NAME, i);
            workerThreads.add(worker);
            executorService.execute(worker);
        }

    }

    static class ShutdownThread extends Thread{
        public void run(){
            workerThreads.forEach(CustomConsumerWorker::shutdown);
            System.out.println("Bye");
        }
    }
}