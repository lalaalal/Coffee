package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.exception.ClientCausedException;
import com.lalaalal.coffee.exception.GeneralException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class ExceptionHandlers extends BaseController {
    @Autowired
    public ExceptionHandlers(HttpSession httpSession) {
        super(httpSession);
    }

    @ExceptionHandler(value = {GeneralException.class, ClientCausedException.class})
    public ResponseEntity<ResultDTO> handleGeneral(GeneralException e) {
        return createResultEntity(e.getResult());
    }
}
