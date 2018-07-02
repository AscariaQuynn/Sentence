package cz.example.sentence.config;

import cz.example.sentence.SentenceException;
import cz.example.sentence.rest.response.SecurityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SecurityResponse handleSecurityException(SecurityException ex) {
        // Zjednoduseni
        ex.printStackTrace();
        return new SecurityResponse(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SecurityResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        // Zjednoduseni
        ex.printStackTrace();
        return new SecurityResponse(ex.getMessage());
    }

    @ExceptionHandler(SentenceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SecurityResponse handleSentenceException(SentenceException ex) {
        // Zjednoduseni
        ex.printStackTrace();
        return new SecurityResponse(ex.getMessage());
    }
}
