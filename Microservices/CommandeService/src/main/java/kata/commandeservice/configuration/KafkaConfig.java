package kata.commandeservice.configuration;


import jakarta.transaction.Transactional;
import kata.shareddto.paimentservice.PaiementRequestDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration

public class KafkaConfig {

    PlatformTransactionManager transactionManager;

    DataSourceTransactionManager dataSourceTransactionManager;
    JpaTransactionManager jpaTransactionManager;
    HibernateTransactionManager hibernateTransactionManager;


    TransactionTemplate transactionTemplate;
@Scope
    @Bean
@Transactional
    public ProducerFactory<String, PaiementRequestDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PaiementRequestDTOSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PaiementRequestDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

