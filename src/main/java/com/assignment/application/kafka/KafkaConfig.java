package com.assignment.application.kafka;

import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import com.assignment.application.entity.Salary;
import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.update.SalaryUpdate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String,EmployeeInfoUpdate> producerFactoryEmpUpdate(){
        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String,EmployeeInfoUpdate>(map);
    }

    @Bean
    public KafkaTemplate<String, EmployeeInfoUpdate> kafkaTemplateEmpUpdate(){
        return new KafkaTemplate(producerFactoryEmpUpdate());
    }

    @Bean
    public ProducerFactory<String, Employee> producerFactoryEmp(){
        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String,Employee>(map);
    }

    @Bean
    public KafkaTemplate<String, Employee> kafkaTemplateEmp(){
        return new KafkaTemplate(producerFactoryEmp());
    }

    @Bean
    public ProducerFactory<String, SalaryUpdate> producerFactoryEmpSalary(){
        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String,SalaryUpdate>(map);
    }

    @Bean
    public KafkaTemplate<String, SalaryUpdate> kafkaTemplateEmpSalary(){
        return new KafkaTemplate(producerFactoryEmpSalary());
    }

    @Bean
    public ConsumerFactory<String, KafkaEmployee> consumerFactory(){
        Map<String,Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringSerializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonSerializer.class);
        map.put(ConsumerConfig.GROUP_ID_CONFIG,"employee");
        return new DefaultKafkaConsumerFactory<>(map,new StringDeserializer(),new JsonDeserializer<>(KafkaEmployee.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,KafkaEmployee> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory containerFactory = new ConcurrentKafkaListenerContainerFactory();
        containerFactory.setConsumerFactory(consumerFactory());
        return containerFactory;
    }

}
