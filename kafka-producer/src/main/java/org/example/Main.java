package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        try{
            // kafka 프로듀서 기본세팅
            Properties configs = new Properties();
            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); //kafka cluster
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //KEY_Serializer
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //VALUE_Serializer
            
            // 위에서 설정한 정보를 통해 kafka 프로듀서 생성
            KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

            int index = 0;

            while (true ){
                
                // 전송할 데이터 설정
                String topic = "test";
                Integer partition = 0;
                String key = "key-" + index;
                String data = "record-" + index;
                
                // 프로듀서 레코드 생성
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);

                // Key도 보낼시 사용
                //ProducerRecord<String, String> record2 = new ProducerRecord<>(topic, key, data);

                // Partition 번호 지정시 사용
                //ProducerRecord<String, String> record3 = new ProducerRecord<>(topic, partition, key, data);

                producer.send(record);

                System.out.println("producer.send() >> [topic:" + topic + "][data:" + data + "]");
                Thread.sleep(1000);
                index++;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}