package com.csis.repository;

import com.csis.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名查询用户（自动生成查询）
     * @param username 用户名
     * @return Optional包装的用户对象
     */
    Optional<User> findByUsername(String username);

    /**
     * 检查用户名是否存在
     * @param username 待检查的用户名
     * @return 是否存在布尔值
     */
    boolean existsByUsername(String username);

    /**
     * 分页查询所有用户
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<User> findAll(Pageable pageable);
}