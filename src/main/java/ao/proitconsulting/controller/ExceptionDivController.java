package ao.proitconsulting.controller;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ao.proitconsulting.exception.DuplicateDateException;
import ao.proitconsulting.exception.Message;

@ControllerAdvice
public class ExceptionDivController {
	
	@ExceptionHandler(DuplicateDateException.class)
    public ResponseEntity<Message> handleValidation(DuplicateDateException ex) {

        Message messageError = new Message();
        messageError.setMessage(ex.getLocalizedMessage());
        messageError.setTime(LocalDateTime.now());
        messageError.setStatus(HttpStatus.BAD_REQUEST.toString());

        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }
}
