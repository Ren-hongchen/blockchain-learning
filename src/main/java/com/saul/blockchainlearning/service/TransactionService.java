package com.saul.blockchainlearning.service;


import com.saul.blockchainlearning.mapper.TransactionMapper;
import com.saul.blockchainlearning.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    public double getUTXO(String public_key) {
        double UTXO = 0.0d;
        List<String> keys = transactionMapper.getAllKeys();
        List<Transaction> transactionList = new ArrayList<>();
        for(String key : keys) {
            Object obj = transactionMapper.get(key);
            if(obj instanceof ArrayList<?>) {
                for(Object o : (List<?>)obj) {
                    transactionList.add(Transaction.class.cast(o));
                }
            }
        }
        for(Transaction transaction : transactionList) {
            if(transaction.getSender().equals(public_key)) {
                UTXO -= transaction.getAmount();
            }
            if(transaction.getRecipient().equals(public_key)) {
                UTXO += transaction.getAmount();
            }
        }

        return UTXO;
    }
}