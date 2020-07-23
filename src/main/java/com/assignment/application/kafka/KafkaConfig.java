package com.assignment.application.kafka;

import com.assignment.application.Constants.StringConstants;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import com.assignment.application.entity.Salary;
import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.update.SalaryUpdate;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Autowired
    private StringConstants stringConstants;

    @Bean
    public ProducerFactory<String,EmployeeInfoUpdate> producerFactoryEmpUpdate(){
        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,stringConstants.localhost);
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
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,stringConstants.localhost);
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
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,stringConstants.localhost);
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
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,stringConstants.localhost);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonSerializer.class);
        map.put(ConsumerConfig.GROUP_ID_CONFIG,"employee");
        ErrorHandlingDeserializer<KafkaEmployee> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(KafkaEmployee.class));

        return new DefaultKafkaConsumerFactory<>(map,new StringDeserializer(),errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,KafkaEmployee> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory containerFactory = new ConcurrentKafkaListenerContainerFactory();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setErrorHandler(new MyErrorHandler());
        return containerFactory;
    }

    public class MyErrorHandler implements ErrorHandler, KafkaListenerErrorHandler {
        @Override
        public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
            return null;
        }

        @Override
        public Object handleError(Message<?> message, ListenerExecutionFailedException ex, Consumer<?, ?> consumer) {
            return null;
        }

        @Override
        public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {

        }
    }

}
