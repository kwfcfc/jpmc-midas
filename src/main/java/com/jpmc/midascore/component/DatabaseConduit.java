package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;

import org.springframework.stereotype.Component;

@Component
public class DatabaseConduit {
    final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DatabaseConduit(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public Balance queryBalance(Long id) {
        Float balance = userRepository.findById(id).map(user -> user.getBalance()).orElse((float) 0);
        return new Balance(balance);
    }

    /*
     * Record transaction and adjust balance for sender and recipient,
     * Adjust the incentive
     */
    public void recordTransaction(TransactionRecord transactionRecord) {
        float amount = transactionRecord.getAmount();
        float incentive = transactionRecord.getIncentive();
        UserRecord sender = userRepository.findById(transactionRecord.getSenderId());
        UserRecord recipient = userRepository.findById(transactionRecord.getRecipientId());

        float senderBalance = sender.getBalance();
        float recipientBalance = recipient.getBalance();
        sender.setBalance(senderBalance - amount);
        recipient.setBalance(recipientBalance + amount + incentive);

        userRepository.save(sender);
        userRepository.save(recipient);
        transactionRepository.save(transactionRecord);
    }

    public boolean validateTransaction(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        return sender.getBalance() >= transaction.getAmount();
    }
}
