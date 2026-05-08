package com.enterprise.bank_lies.service;

import com.enterprise.bank_lies.entity.BalanceOfUser;
import com.enterprise.bank_lies.entity.BankDataAccountUser;
import com.enterprise.bank_lies.entity.TransferOfUser;
import com.enterprise.bank_lies.exceptions.InsufficientBalanceException;
import com.enterprise.bank_lies.exceptions.NotFoundUserException;
import com.enterprise.bank_lies.repository.AccountUserRepository;
import com.enterprise.bank_lies.repository.BalanceRepository;
import com.enterprise.bank_lies.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class TransferService {

    @Autowired
    private AccountUserRepository accountUserRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    @Transactional
    public void transferByEmail(Integer senderId, String receiverEmail, BigDecimal amount) {
        BankDataAccountUser receiver = accountUserRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new NotFoundUserException("Receiver not found."));

        executeCoreTransfer(senderId, receiver, amount);
    }

    @Transactional
    public void transferByNumber(Integer senderId, String receiverNumber, BigDecimal amount) {
        BankDataAccountUser receiver = accountUserRepository.findByNumber(receiverNumber)
                .orElseThrow(() -> new NotFoundUserException("Receiver not found."));

        executeCoreTransfer(senderId, receiver, amount);
    }

    private void executeCoreTransfer(Integer senderId, BankDataAccountUser receiver, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid transfer amount.");
        }

        BankDataAccountUser sender = accountUserRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundUserException("Sender not found."));

        if (senderId.equals(receiver.getId())) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same.");
        }

        BalanceOfUser senderBalance = balanceRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender balance not found."));
        BalanceOfUser receiverBalance = balanceRepository.findById(receiver.getId())
                .orElseThrow(() -> new RuntimeException("Receiver balance not found."));

        if (senderBalance.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        senderBalance.setBalance(senderBalance.getBalance().subtract(amount));
        receiverBalance.setBalance(receiverBalance.getBalance().add(amount));

        balanceRepository.save(senderBalance);
        balanceRepository.save(receiverBalance);

        TransferOfUser history = TransferOfUser.builder()
                .sender(sender)
                .receiver(receiver)
                .value(amount)
                .time(LocalDateTime.now())
                .build();

        transferRepository.save(history);
    }
}