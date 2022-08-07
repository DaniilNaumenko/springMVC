package by.naumenka.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class, EventNotFoundException.class, TicketNotFoundException.class})
    public ResponseEntity<String> handleApplicationException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(GlobalApplicationException.class)
    public ResponseEntity<String> handleApplicationException(GlobalApplicationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}