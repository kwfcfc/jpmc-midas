package com.jpmc.midascore.entity;

import com.jpmc.midascore.foundation.Transaction;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private long senderId;

    @Column(nullable = false)
    private long recipientId;

    @Column(nullable = false)
    private float amount;

    protected TransactionRecord() {        
    }

    public TransactionRecord(Transaction transaction) {
        this.senderId = transaction.getSenderId();
        this.recipientId = transaction.getRecipientId();
        this.amount = transaction.getAmount();
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%d, senderId=%d, recipient=%d, amouunt='%f']", id, senderId, recipientId, amount);
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public float getAmount() {
        return amount;
    }
}
