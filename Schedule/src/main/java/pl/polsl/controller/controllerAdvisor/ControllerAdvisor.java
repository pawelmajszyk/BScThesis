package pl.polsl.controller.controllerAdvisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.polsl.exception.NoAuthorityForWorkerException;
import pl.polsl.exception.ScheduleMissingException;
import pl.polsl.exception.ScheduleOverlapsTime;
import pl.polsl.exception.UserMissingException;


import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoAuthorityForWorkerException.class)
    public ResponseEntity<Object> handeMissingCinema(NoAuthorityForWorkerException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Manager has no authority over worker";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(ScheduleOverlapsTime.class)
    public ResponseEntity<Object> handeMissingCinemaHall(ScheduleOverlapsTime ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Schedule overlaps with existing schedule";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(UserMissingException.class)
    public ResponseEntity<Object> handeMissingSeat(UserMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "User with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(ScheduleMissingException.class)
    public ResponseEntity<Object> handeMissingSeat(ScheduleMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Schedule with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    private Map<String, Object> buildMessageBody(String msg) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", msg);

        return body;
    }
}
