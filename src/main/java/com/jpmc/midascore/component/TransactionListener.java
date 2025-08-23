package com.jpmc.midascore.component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionListener.class);

    @KafkaListener(groupId = "${general.kafka-group-id}", topics = "${general.kafka-topic}")
    public void listen(String record) {
        log.info("Got '{}'", record);
    }
}
