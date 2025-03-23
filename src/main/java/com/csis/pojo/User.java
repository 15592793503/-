package com.csis.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id//标识为著字段
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键生成策略（自增）
    private Long id;

    @Column(unique = true, nullable = false)//非空且唯一
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "create_time")
    private LocalDateTime createTime; // 用户创建时间
}
