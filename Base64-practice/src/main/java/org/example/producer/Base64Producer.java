package org.example.producer;

import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.Base64FileEncoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Properties;

public class Base64Producer {
    public static void main(String[] args){

        try{
            Properties producerConfig = new Properties();
            producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
            KafkaProducer<String, byte[]> base64Producer = new KafkaProducer<String, byte[]>(producerConfig);
            int i = 0;

            String topic = "Base64-Test";
            Integer partition = 1;


            File file = new File("C:\\Users\\User\\Downloads\\wallpaperbetter2.jpg");

            byte[] binary = Base64FileEncoding.getFileBinary("C:\\Users\\User\\Downloads\\wallpaperbetter2.jpg");
            //String data = Base64.getEncoder().encodeToString(binary);
            byte[] encoded = Base64.getEncoder().encode(binary);
            //String test = Base64.getEncoder().encodeToString(binary);

            ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, encoded);

            base64Producer.send(record);
            base64Producer.flush();
            base64Producer.close();

            byte[] decoded = Base64.getDecoder().decode(encoded);




        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
