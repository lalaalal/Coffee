package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.dto.UserDTO;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SuppressWarnings("unused")
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, HttpSession httpSession) {
        super(httpSession);
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResultDTO> login(
            @RequestParam String id, @RequestParam String password,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Result result = userService.login(id, password);
        ResponseEntity<ResultDTO> responseEntity = createResultEntity(result);
        if (!result.status().is2xxSuccessful())
            return responseEntity;
        UserDTO user = userService.getUser(id);
        session.setAttribute("login", user);
        session.setAttribute("lang", user.getLanguage());
        session.setMaxInactiveInterval(60 * 30);
        return responseEntity;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<ResultDTO> signUp(
            @RequestParam String id, @RequestParam String password) {
        Result result = userService.signUp(id, password);

        return createResultEntity(result);
    }
}
