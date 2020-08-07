package com.assignment.application.kafka;

import com.assignment.application.constants.StringConstant;
import com.assignment.application.dto.EmployeeDTO;
import com.assignment.application.entity.Employee;
import com.assignment.application.entity.KafkaEmployee;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
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
public class KafkaConsumerConfig {

    private static final Logger LOG = LogManager.getLogger(KafkaConsumerConfig.class);

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "employee");
        ErrorHandlingDeserializer<Object> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Object.class));

        return new DefaultKafkaConsumerFactory<>(map, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory containerFactory = new ConcurrentKafkaListenerContainerFactory();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setErrorHandler(new KafkaErrorHandler());
        return containerFactory;
    }

//    @Bean
//    public ConsumerFactory<String, EmployeeDTO> consumerEmpFactory() {
//        Map<String, Object> map = new HashMap<>();
//        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, StringConstant.HOSTNAME);
//        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        map.put(ConsumerConfig.GROUP_ID_CONFIG, "employee");
//        ErrorHandlingDeserializer<EmployeeDTO> errorHandlingDeserializer =
//                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(EmployeeDTO.class));
//
//        return new DefaultKafkaConsumerFactory<>(map, new StringDeserializer(), errorHandlingDeserializer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, EmployeeDTO> concurrentEmpKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory containerFactory = new ConcurrentKafkaListenerContainerFactory();
//        containerFactory.setConsumerFactory(consumerFactory());
//        //containerFactory.setErrorHandler(new KafkaErrorHandler());
//        return containerFactory;
//    }

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
