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
 * Helps create ResponseEntity and gives session data.
 *
 * @see BaseController#BaseController(HttpSession)
 * @see BaseController#createResponseEntity(Object, HttpStatus)
 * @see BaseController#createResultEntity(Result)
 * @see BaseController#currentUser()
 * @see BaseController#getUserLanguage()
 * @author lalaalal
 */
public abstract class BaseController {
    protected final HttpSession httpSession;

    /**
     * <pre>
     * {@code
     * @Autowired
     * public DerivedController(HttpSession httpSession, ...) {
     *     super(httpSession);
     *     ...
     * }
     * }
     * </pre>
     *
     * @param httpSession Bean of HttpSession
     */
    protected BaseController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * Make ResponseEntity<ResultDTO> with Result.<br>
     * Message of Result will be translated by session language and create ResultDTO.<br>
     * Sets content type to "application/json".
     *
     * @param result A Result not null
     * @return ResponseEntity<ResponseDTO> made of Result
     * @see Result
     * @see ResultDTO
     * @see BaseController#createResponseEntity(Object, HttpStatus)
     */
    protected ResponseEntity<ResultDTO> createResultEntity(Result result) {
        return createResponseEntity(result.toResultDTO(getUserLanguage()), result.status());
    }

    /**
     * Make received object to ResponseEntity<T>.<br>
     * Sets content type to "application/json".
     *
     * @param t      An object serializable with <b>ObjectMapper</b>
     * @param status HttpStatus ResponseEntity contains
     * @return ResponseEntity based on t and status
     */
    protected <T> ResponseEntity<T> createResponseEntity(T t, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(t, headers, status);
    }

    /**
     * Current user's language stored in HttpSession.
     *
     * @return Current language<br> Server default language if anonymous user
     * @see UserDTO
     * @see UserDTO#getLanguage()
     */
    protected Language getUserLanguage() {
        Language language = (Language) httpSession.getAttribute("lang");
        if (language == null)
            return UserDTO.ANONYMOUS.getLanguage();
        return language;
    }

    /**
     * User stored in HttpSession.
     *
     * @return Current user {@link UserDTO#ANONYMOUS} by default
     * @see UserDTO
     */
    protected UserDTO currentUser() {
        UserDTO user = (UserDTO) httpSession.getAttribute("login");
        if (user == null)
            return UserDTO.ANONYMOUS;
        return user;
    }
}
