package com.csis.study3shoppingcenter.pojo;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "product") // 显式指定表名
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL使用自增主键
    private Long id;

    @Column(nullable = false, length = 100) // 增加字段约束
    private String name;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Version
    @Column(nullable = false)
    private int version;

}
