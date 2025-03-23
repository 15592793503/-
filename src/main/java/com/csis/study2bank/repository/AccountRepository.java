package com.csis.study2bank.repository;

import com.csis.study2bank.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * 账户数据访问层
 * 继承JpaRepository获得基础CRUD能力
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 根据账号查询账户（Spring Data JPA自动实现）
     * @param accountNumber 银行账号（唯一）
     * @return Optional包装的账户对象
     */
    Optional<Account> findByAccountNumber(String accountNumber);
}