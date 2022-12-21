package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class CustomConsumerWorker implements Runnable{
    private Properties prop;
    private String topic;
    private String threadName;
    private KafkaConsumer<String, String> consumer;

    CustomConsumerWorker(Properties prop, String topic, int number){
        this.prop = prop;
        this.topic = topic;
        this.threadName = "consumer-thread-" + number;
    }

    @Override
    public void run() {
        consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList(topic));
        File file = new File(threadName + ".csv");

        try(FileWriter fw = new FileWriter(file, true);){
            while(true){
                StringBuilder fileWriteBuffer = new StringBuilder();
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String,String> record : records){
                    fileWriteBuffer.append(record.value()).append("\n");
                }
                fw.write(fileWriteBuffer.toString());
                consumer.commitAsync();
            }
        } catch (IOException e) {
            System.err.println(threadName + "IOException" + e);
        } catch (WakeupException e){
            System.out.println(threadName + "WakeupException");
        } finally {
            consumer.commitAsync();
            consumer.close();
        }
    }

    public void shutdown(){
        consumer.wakeup();
    }
}
