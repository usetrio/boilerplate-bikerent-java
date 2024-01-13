package com.trio.java.bikerentapi.error;

import com.trio.java.bikerentapi.exception.BikeNotFoundException;
import com.trio.java.bikerentapi.exception.MaxBikeLoadExceededException;
import com.trio.java.bikerentapi.exception.UnavailableBikeException;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BikeNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUnavailableBikeException(BikeNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MaxBikeLoadExceededException.class)
  public ResponseEntity<ErrorResponse> handleUnavailableBikeException(
      MaxBikeLoadExceededException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
  }

  @ExceptionHandler(UnavailableBikeException.class)
  public ResponseEntity<ErrorResponse> handleUnavailableBikeException(
      UnavailableBikeException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    val errors = new HashSet<ErrorResponse>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      val fieldName = ((FieldError) error).getField();
      val errorMessage = error.getDefaultMessage();
      val stringBuilder = new StringBuilder();
      errors.add(new ErrorResponse(
          stringBuilder.append(fieldName).append(" ").append(errorMessage).toString()));
    });
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
    log.error("Unexpected error", ex);
    return ResponseEntity.internalServerError()
        .body(new ErrorResponse("Sorry, an unexpected error occurred."));
  }
}
