package com.assignment.application.kafka;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import com.assignment.application.entity.Salary;
import com.assignment.application.update.EmployeeInfoUpdate;
import com.assignment.application.update.SalaryUpdate;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    private static final Logger LOG = LogManager.getLogger(KafkaConfig.class);

    @Autowired
    private StringConstant stringConstant;


    @Bean
    public ProducerFactory<String,EmployeeInfoUpdate> producerFactoryEmpUpdate(){
        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
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
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
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
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String,SalaryUpdate>(map);
    }

    @Bean
    public KafkaTemplate<String, SalaryUpdate> kafkaTemplateEmpSalary(){
        return new KafkaTemplate(producerFactoryEmpSalary());
    }

    @Bean
    public ProducerFactory<String, Salary> producerFactoryEmpSalaryUpdate(){
        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String,Salary>(map);
    }

    @Bean
    public KafkaTemplate<String, Salary> kafkaTemplateEmpSalaryUpdate(){
        return new KafkaTemplate(producerFactoryEmpSalaryUpdate());
    }


    @Bean
    public ConsumerFactory<String, KafkaEmployee> consumerFactory(){
        Map<String,Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
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
        containerFactory.setErrorHandler(new KafkaErrorHandler());
        return containerFactory;
    }

    public class KafkaErrorHandler implements ErrorHandler, KafkaListenerErrorHandler {
        @Override
        public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
            LOG.info(message.toString());
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
