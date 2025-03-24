package com.csis.study3shoppingcenter.service;

import com.csis.study3shoppingcenter.pojo.Product;
import com.csis.study3shoppingcenter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;

    // 乐观锁购买方法（带重试机制）
    @Retryable(  // Spring Retry重试机制配置
            value = ObjectOptimisticLockingFailureException.class, // 捕获乐观锁异常
            maxAttempts = 5,  // 最大重试次数
            backoff = @Backoff(delay = 100)  // 重试间隔(ms)
    )
    @Transactional
    public void purchaseOptimistic(Long productId, int quantity) {
        // 普通查询（无锁）
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        // 检查库存（此时数据可能已被其他事务修改）
        if (product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);  // 保存时会检查@Version版本号
        } else {
            throw new RuntimeException("库存不足");
        }
    }

    // 悲观锁购买方法
    @Transactional
    public void purchasePessimistic(Long productId, int quantity) {
        // 使用悲观锁查询（立即获取数据库锁）
        Product product = productRepository.findByIdWithPessimisticLock(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        // 检查库存（此时数据已被锁定，保证数据一致性）
        if (product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);  // 悲观锁模式下版本号也会更新
        } else {
            throw new RuntimeException("库存不足");
        }
    }
}