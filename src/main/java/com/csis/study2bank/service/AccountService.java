package com.csis.study2bank.service;

import com.csis.study2bank.pojo.Account;
import com.csis.study2bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;


    /**
     * 转账业务方法
     * @Transactional 注解开启事务管理
     * isolation = Isolation.REPEATABLE_READ 设置事务隔离级别
     * 默认遇到RuntimeException时回滚事务
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transfer(String fromAccountNumber,
                         String toAccountNumber,
                         BigDecimal amount) {
        // 查询转出账户（此时会加共享锁，REPEATABLE_READ级别保证可重复读）
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("转出账户不存在"));
        // 查询转入账户
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("转入账户不存在"));
        // 业务校验：余额是否充足（使用BigDecimal.compareTo避免精度问题）
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("账户余额不足");
        }
        // 执行转账操作（内存中修改余额）
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        // 显式保存（实际JPA脏检查会自动更新，此处明确操作更清晰）
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        // 注意：事务提交时才会真正执行数据库更新
    }

    /**
     * 查询账户余额
     * 不开启事务，使用数据库默认读提交隔离级别
     */
    public BigDecimal getBalance(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("账户不存在"))
                .getBalance();
    }
}