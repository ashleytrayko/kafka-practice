package org.example.consumer;

import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.util.*;

public class Base64Consumer {
    public static void main(String[] args) {
        File file = new File("kafkaimg3.jpg");
        KafkaConsumer<String, byte[]> base64Consumer = null;
        try (FileWriter fw = new FileWriter(file, true)) {
            Properties consumerConfig = new Properties();
            consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
            consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "base64");

            base64Consumer = new KafkaConsumer<String, byte[]>(consumerConfig);

            base64Consumer.subscribe(Arrays.asList("Base64-Test"));
            final int minBatchSize = 1024;
            StringBuilder fileWriteBuffer = new StringBuilder();
            ConsumerRecords<String, byte[]> records = base64Consumer.poll(Duration.ofSeconds(1));
            byte[] loadedBytes = null;
            for (ConsumerRecord<String, byte[]> record : records) {
                loadedBytes = new byte[record.value().length];
                loadedBytes = record.value();
                //fileWriteBuffer.append(record.value());
            }

            //String data = new String(fileWriteBuffer);

            byte[] decoded = Base64.getDecoder().decode(loadedBytes);
            FileUtils.writeByteArrayToFile(file, decoded);
//                fw.write(new String(decoded)); [B@671a5887 [B@342c38f8
            base64Consumer.commitSync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            base64Consumer.commitSync();
            base64Consumer.close();
        }
    }
}
