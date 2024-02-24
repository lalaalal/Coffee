package com.lalaalal.coffee.controller.api;

import com.lalaalal.coffee.controller.SessionHelper;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.dto.UserDTO;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserApiController extends SessionHelper {
    private final UserService userService;

    @Autowired
    public UserApiController(UserService userService, HttpSession httpSession) {
        super(httpSession);
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResultDTO> login(
            @RequestParam("id") String id, @RequestParam("password") String password) {
        Result result = userService.login(id, password);
        ResponseEntity<ResultDTO> responseEntity = createResultEntity(result);
        if (!result.status().is2xxSuccessful())
            return responseEntity;
        UserDTO user = userService.getUser(id);
        httpSession.setAttribute("login", user);
        httpSession.setAttribute("lang", user.getLanguage());
        httpSession.setMaxInactiveInterval(60 * 30);
        return responseEntity;
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ResultDTO> logout() {
        if (currentUser() == UserDTO.ANONYMOUS)
            // TODO : translate
            return createResultEntity(Result.failed("result.message.failed.login_first"));
        httpSession.removeAttribute("login");
        httpSession.removeAttribute("lang");
        httpSession.setMaxInactiveInterval(0);
        return createResultEntity(Result.succeed("result.message.succeed.logout"));
    }

    @PostMapping("/sign_up")
    public ResponseEntity<ResultDTO> signUp(
            @RequestParam("id") String id, @RequestParam("password") String password) {
        Result result = userService.signUp(id, password);

        return createResultEntity(result);
    }

    @GetMapping("/whoami")
    public String whoami() {
        UserDTO user = currentUser();

        return user.getId();
    }
}
