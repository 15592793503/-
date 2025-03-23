package com.csis.controller;


import com.csis.pojo.User;
import com.csis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 创建用户
     * @param user 请求体中包含的用户数据
     * @return 201 Created 状态码及创建的用户数据
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    /**
     * 分页获取用户列表
     * @param page 页码（默认0）
     * @param size 每页数量（默认10）
     * @param sort 排序参数（格式："字段名,asc|desc"）
     * @return 分页用户数据
     */
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // 解析排序参数
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
    /**
     * 根据ID获取单个用户
     * @param id 用户ID
     * @return 用户数据
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    /**
     * 更新用户信息
     * @param id 要更新的用户ID
     * @param userDetails 包含更新数据的用户对象
     * @return 更新后的用户数据
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id, @Valid @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    /**
     * 删除用户
     * @param id 要删除的用户ID
     * @return 204 No Content 状态码
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
