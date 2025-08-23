package com.jpmc.midascore.component;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.foundation.Transaction;

@Component
public class TransactionListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionListener.class);

    @KafkaListener(groupId = "${general.kafka-group-id}", topics = "${general.kafka-topic}")
    public void listen(ConsumerRecord<String, Transaction> record) {
        Transaction tx = record.value();
        log.info("Got '{}'", tx.toString());
    }
}
