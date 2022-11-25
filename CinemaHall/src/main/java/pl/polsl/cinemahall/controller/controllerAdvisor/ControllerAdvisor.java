package pl.polsl.cinemahall.controller.controllerAdvisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.polsl.cinemahall.exception.CinemaMissingException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CinemaMissingException.class)
    public ResponseEntity<Object> handeMissingTopic(CinemaMissingException ex) {
        log.error(ex.getMessage(), ex);

        String msg = "Cinema with given id is missing";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(buildMessageBody(msg), httpStatus);
    }

    private Map<String, Object> buildMessageBody(String msg) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", msg);

        return body;
    }
}
