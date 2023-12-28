package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.exception.GeneralException;
import com.lalaalal.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers extends BaseController {
    @Autowired
    public ExceptionHandlers(UserService userService) {
        super(userService);
    }

    @ExceptionHandler
    public ResponseEntity<ResultDTO> handle(GeneralException e) {
        return createResultEntity(e.getResult());
    }
}
