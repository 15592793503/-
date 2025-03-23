package com.csis.study2bank.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "account") // 指定数据库表名
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增策略
    private Long id;

    @Column(unique = true, nullable = false) // 唯一约束+非空约束
    private String accountNumber;

    @Column(nullable = false)
    private String accountName;

    @Column(precision = 19, scale = 2, nullable = false) // 金额精度控制（总位数19，小数位2）
    private BigDecimal balance = BigDecimal.ZERO; // 初始化余额为0
}
