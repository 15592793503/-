package com.csis.study3shoppingcenter.repository;

import com.csis.study3shoppingcenter.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * 悲观锁查询方法（需要在事务中调用）
     * @Lock 指定锁模式为PESSIMISTIC_WRITE（行级排他锁）
     * 注意：不同数据库实现可能不同（如MySQL使用FOR UPDATE）
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") // 锁超时3秒
    })
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithPessimisticLock( @Param("id")Long id);
}
