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

    @Column
    private float incentive;

    protected TransactionRecord() {        
    }

    public TransactionRecord(Transaction transaction) {
        this.senderId = transaction.getSenderId();
        this.recipientId = transaction.getRecipientId();
        this.amount = transaction.getAmount();
        this.incentive = 0;
    }

    public TransactionRecord(Transaction transaction, float incentive) {
        this.senderId = transaction.getSenderId();
        this.recipientId = transaction.getRecipientId();
        this.amount = transaction.getAmount();
        this.incentive = incentive;
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%d, senderId=%d, recipient=%d, amouunt='%f']", id, senderId, recipientId, amount);
    }

    public long getId() {
        return id;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public float getAmount() {
        return amount;
    }

    public float getIncentive() {
        return incentive;
    }
}
