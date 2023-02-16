package com.project.jpaproject.order.controller;

import com.project.jpaproject.order.ApiResponse;
import com.project.jpaproject.order.dto.OrderDto;
import com.project.jpaproject.order.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/orders")
    public ApiResponse<String> save(@RequestBody OrderDto orderDto) {
        String uuid = orderService.save(orderDto);
        return ApiResponse.ok(uuid);
    }

    @GetMapping("/orders/{uuid}")
    public ApiResponse<OrderDto> getOne(@PathVariable String uuid) throws NotFoundException {
        OrderDto one = orderService.findOne(uuid);
        return ApiResponse.ok(one);
    }

    @GetMapping("/orders")
    public ApiResponse<Page<OrderDto>> getAll(Pageable pageable) {
        Page<OrderDto> all = orderService.findAll(pageable);
        return ApiResponse.ok(all);
    }

}
