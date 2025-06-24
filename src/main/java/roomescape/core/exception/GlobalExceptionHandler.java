package roomescape.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    Throwable cause = e.getCause();
    while (cause != null) {
      if (cause instanceof ApplicationException applicationException) {
        CustomErrorCode errorCode = applicationException.getCustomErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
      }
      cause = cause.getCause();
    }

    ErrorResponse errorResponse = ErrorResponse.of(CustomErrorCode.INVALID_REQUEST_FORMAT);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
    CustomErrorCode errorCode = e.getCustomErrorCode();
    ErrorResponse errorResponse = ErrorResponse.of(errorCode);
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
    System.out.println(e.getMessage());

    return ResponseEntity.internalServerError()
        .body(ErrorResponse.of(CustomErrorCode.INTERNAL_SERVER_ERROR));
  }
}
