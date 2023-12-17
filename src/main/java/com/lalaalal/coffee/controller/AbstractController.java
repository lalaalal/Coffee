package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

public abstract class AbstractController {
    protected final UserService userService;

    protected AbstractController(UserService userService) {
        this.userService = userService;
    }

    protected ResponseEntity<String> createResultEntity(Result result) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        String text = result.translatedJsonString(getLanguage());
        return new ResponseEntity<>(text, headers, result.status());
    }

    protected Language getLanguage() {
        return userService.getLanguage();
    }
}
