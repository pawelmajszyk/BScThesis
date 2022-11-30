package pl.polsl.screening.controller.controllerAdvisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.polsl.screening.exception.CinemaHallMissingException;
import pl.polsl.screening.exception.MovieMissingException;
import pl.polsl.screening.exception.ScreeningMissingException;


import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MovieMissingException.class)
    public ResponseEntity<Object> handeMissingCinema(MovieMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Movie with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(CinemaHallMissingException.class)
    public ResponseEntity<Object> handeMissingCinema(CinemaHallMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Cinema with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(ScreeningMissingException.class)
    public ResponseEntity<Object> handeMissingCinema(ScreeningMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Screening with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    private Map<String, Object> buildMessageBody(String msg) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", msg);

        return body;
    }
}
