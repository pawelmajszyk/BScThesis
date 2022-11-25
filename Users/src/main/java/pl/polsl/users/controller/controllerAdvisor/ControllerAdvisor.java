package pl.polsl.users.controller.controllerAdvisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.polsl.users.exceptions.CinemaNotFoundException;
import pl.polsl.users.exceptions.DuplicateUserException;
import pl.polsl.users.exceptions.UserNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Object> handeMissingTopic(DuplicateUserException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Username or email duplicated";
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handeMissingTopic(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "User not found" + ex.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(CinemaNotFoundException.class)
    public ResponseEntity<Object> handleMissingCInema(CinemaNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Cinema not found" + ex.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    private Map<String, Object> buildMessageBody(String msg) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", msg);

        return body;
    }
}
