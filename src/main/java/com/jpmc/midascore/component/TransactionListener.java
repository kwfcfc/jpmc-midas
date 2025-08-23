package com.jpmc.midascore.component;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.foundation.Transaction;

@Component
public class TransactionListener {

    @Autowired
    private DatabaseConduit databaseConduit;

    @Autowired
    private IncentiveFetch incentiveFetch;

    private static final Logger log = LoggerFactory.getLogger(TransactionListener.class);

    TransactionListener() {}

    @KafkaListener(groupId = "${general.kafka-group-id}", topics = "${general.kafka-topic}")
    public void listen(ConsumerRecord<String, Transaction> record) {
        Transaction tx = record.value();
        log.info("Got {}", tx.toString());
        if (databaseConduit.validateTransaction(tx)) {
            float incentive = incentiveFetch.fetch(tx);
            databaseConduit.recordTransaction(new TransactionRecord(tx, incentive));
        }
    }
}
