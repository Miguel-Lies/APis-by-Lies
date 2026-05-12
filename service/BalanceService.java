package com.enterprise.bank_lies.service;

import com.enterprise.bank_lies.entity.BalanceOfUser;
import com.enterprise.bank_lies.exceptions.InsufficientBalanceException;
import com.enterprise.bank_lies.exceptions.NotFoundUserException;
import com.enterprise.bank_lies.repository.AccountUserRepository;
import com.enterprise.bank_lies.repository.BalanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService {

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    AccountUserRepository accountUserRepository;

    @Transactional
    public void addBalance(Integer id, BigDecimal amount) {
        if (!accountUserRepository.existsById(id)) {
            throw new NotFoundUserException("Not found user, try again.");
        }
        BalanceOfUser balanceOfUser = balanceRepository.findById(id)
                .orElseGet(() -> {
                    BalanceOfUser newBalance = new BalanceOfUser();
                    newBalance.setId(id);
                    newBalance.setBalance(BigDecimal.ZERO);
                    newBalance.setInvestment(BigDecimal.ZERO);
                    return newBalance;
                });

        balanceOfUser.setBalance(balanceOfUser.getBalance().add(amount));

        balanceRepository.save(balanceOfUser);
    }

    @Transactional
    public void addInvestment(Integer id, BigDecimal amount) {
        BalanceOfUser balanceOfUser = balanceRepository.findById(id)
                .orElseThrow(()
                        -> new RuntimeException("Not found user, try again."));

        if (balanceOfUser.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
        balanceOfUser.setBalance(amount);
        balanceOfUser.setInvestment(amount);

        balanceRepository.save(balanceOfUser);

    }

    @Transactional
    public void withdraw(Integer id, BigDecimal amount){
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The withdrawal amount must be positive.");
        }
        BalanceOfUser balanceOfUser=balanceRepository.findById(id)
                .orElseThrow(()->new NotFoundUserException("Noy found user, try again."));
        if (balanceOfUser.getBalance().compareTo(amount)<0){
            throw new InsufficientBalanceException("You don't have that balance, try again.");
        }
        balanceOfUser.setBalance(balanceOfUser.getBalance().subtract(amount));

        balanceRepository.save(balanceOfUser);
    }
}