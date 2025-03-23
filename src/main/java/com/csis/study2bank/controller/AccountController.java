package com.csis.study2bank.controller;

import com.csis.study2bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;


    /**
     * 查询余额接口
     * @param accountNumber 路径参数-账号
     * @return HTTP 200 + 余额数值
     */
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    /**
     * 转账接口
     * @param fromAccountNumber 转出账号（请求参数）
     * @param toAccountNumber 转入账号
     * @param amount 转账金额
     * @return
     */
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam BigDecimal amount) {
        // 金额必须大于0（基础校验）
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("转账金额必须大于0");
        }
        accountService.transfer(fromAccountNumber, toAccountNumber, amount);
        return ResponseEntity.ok("转账成功");
    }
}
