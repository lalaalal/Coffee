package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/test")
@SpringBootTest
@SuppressWarnings("unused")
public class TestController extends SessionHelper {
    @Autowired
    public TestController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping("/hello")
    public ResponseEntity<ResultDTO> hello() {
        return createResultEntity(Result.SUCCEED);
    }

    @GetMapping("/order")
    public ResponseEntity<Order> order() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Order order = new Order();
        OrderItem item = new OrderItem("americano");
        order.add(item);
        return new ResponseEntity<>(order, headers, HttpStatus.OK);
    }
}