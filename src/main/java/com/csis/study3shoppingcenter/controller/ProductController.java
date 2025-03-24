package com.csis.study3shoppingcenter.controller;

import com.csis.study3shoppingcenter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase") // 明确指定路径
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/optimistic")
    public ResponseEntity<String> purchaseOptimistic(
            @RequestParam Long productId,
            @RequestParam int quantity) {
        try {
            productService.purchaseOptimistic(productId, quantity);
            return ResponseEntity.ok("购买成功");
        } catch (ObjectOptimisticLockingFailureException e) {
            // 乐观锁冲突：HTTP 409 Conflict状态码
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("请求冲突，请重试");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/pessimistic")
    public ResponseEntity<String> purchasePessimistic(
            @RequestParam Long productId,
            @RequestParam int quantity) {
        try {
            productService.purchasePessimistic(productId, quantity);
            return ResponseEntity.ok("购买成功");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}