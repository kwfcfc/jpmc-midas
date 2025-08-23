package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DatabaseConduit(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void record(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());
        float amount = transaction.getAmount();
        float senderBalance = sender.getBalance();
        float recipientBalance = recipient.getBalance();

        if (amount <= senderBalance) {
            sender.setBalance(senderBalance - amount);
            recipient.setBalance(recipientBalance + amount);
            userRepository.save(sender);
            userRepository.save(recipient);
            transactionRepository.save(new TransactionRecord(transaction));
        }
    }
}
