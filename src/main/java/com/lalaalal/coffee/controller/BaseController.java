package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.ResultDTO;
import com.lalaalal.coffee.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

public abstract class BaseController {
    protected final UserService userService;

    protected BaseController(UserService userService) {
        this.userService = userService;
    }

    protected ResponseEntity<ResultDTO> createResultEntity(Result result) {
        return createResponseEntity(result.toResultDTO(getLanguage()), result.status());
    }

    protected <T> ResponseEntity<T> createResponseEntity(T t, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(t, headers, status);
    }

    protected Language getLanguage() {
        return userService.getLanguage();
    }
}
