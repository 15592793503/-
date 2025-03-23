package com.csis.service;

import com.csis.pojo.User;
import com.csis.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 构造函数注入依赖
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 创建新用户
     * @param user 用户对象（包含明文密码）
     * @return 已保存的用户对象（含加密密码）
     * @throws RuntimeException 用户名已存在时抛出异常
     */
    @Transactional
    public User createUser(User user) {
        // 检查用户名唯一性
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        if (user.getPassword()==null || user.getPassword() ==""){
            throw new RuntimeException("密码为空");
        }
        // 加密密码并保存
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    /**
     * 分页查询用户列表
     * @param pageable 分页参数（页码、尺寸、排序）
     * @return 分页结果
     */
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户实体
     * @throws RuntimeException 用户不存在时抛出异常
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param userDetails
     * @return
     * @throws RuntimeException
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        // 用户名不可修改
        if (userDetails.getUsername() != null &&
                !userDetails.getUsername().equals(user.getUsername())) {
            throw new RuntimeException("用户名不可修改");
        }
        // 密码更新逻辑（仅当传入新密码时更新）
        if (userDetails.getPassword() != null &&
                !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }else {
            throw new RuntimeException("修改密码不能为空");
        }
        user.setName(userDetails.getName());
        return userRepository.save(user);
    }

    /**
     * 删除用户
     * @param id
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}