package pl.polsl.cinemahall.controller.controllerAdvisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.polsl.cinemahall.exception.CinemaHallMissingException;
import pl.polsl.cinemahall.exception.CinemaMissingException;
import pl.polsl.cinemahall.exception.SeatMissingException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CinemaMissingException.class)
    public ResponseEntity<Object> handeMissingCinema(CinemaMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Cinema with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(CinemaHallMissingException.class)
    public ResponseEntity<Object> handeMissingCinemaHall(CinemaHallMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "CinemaHall with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    @ExceptionHandler(SeatMissingException.class)
    public ResponseEntity<Object> handeMissingSeat(SeatMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Seat with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    private Map<String, Object> buildMessageBody(String msg) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", msg);

        return body;
    }
}
