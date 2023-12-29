package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.dto.UserDTO;
import com.lalaalal.coffee.model.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

/**
 * ResponseEntity 의 생성을 도와주는 추상 클래스.
 *
 * @author lalaalal
 */
public abstract class BaseController {
    protected final HttpSession httpSession;

    protected BaseController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * Result 를 ResponseEntity<ResultDTO> 로 만들어준다.<br>
     * Result 의 Message 는 사용자에 따라 번역되어 ResultDTO 로 전달된다.<br>
     * ContentType 은 application/json 이다.
     *
     * @param result null 이 아닌 Result 객체
     * @return result 를 통해 생성된 ResponseEntity<ResponseDTO>
     * @see BaseController#createResponseEntity(Object, HttpStatus)
     */
    protected ResponseEntity<ResultDTO> createResultEntity(Result result) {
        return createResponseEntity(result.toResultDTO(getUserLanguage()), result.status());
    }

    /**
     * 전달받은 객체를 ResponseEntity<T> 로 만들어준다.<br>
     * ContentType 은 application/json 이다.
     *
     * @param t      ResponseEntity 에 담길 객체로 <p>ObjectMapper 로 <b>직렬화가 가능한 타입<b/></p>
     * @param status ResponseEntity 에 담길 상태
     * @param <T>    ObjectMapper 로 직렬화가 가능한 타입이어야 한다.
     * @return t 와 status 에 기반한 ResponseEntity 를 반환
     */
    protected <T> ResponseEntity<T> createResponseEntity(T t, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(t, headers, status);
    }

    protected Language getUserLanguage() {
        return currentUser().getLanguage();
    }

    protected UserDTO currentUser() {
        UserDTO user = (UserDTO) httpSession.getAttribute("login");
        if (user == null)
            return UserDTO.ANONYMOUS;
        return user;
    }
}
