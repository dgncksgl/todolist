package tr.com.hepsiemlak.todolist.shared.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tr.com.hepsiemlak.todolist.shared.exception.entity.ExceptionEntity;
import tr.com.hepsiemlak.todolist.shared.exception.type.AlreadyExistException;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;
import tr.com.hepsiemlak.todolist.shared.exception.type.TokenRefreshException;

import java.io.NotActiveException;
import java.util.UUID;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionEntity> handleAllException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionEntity.getExceptionEntity()
                                .setId(UUID.randomUUID().toString())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionEntity> handleException(ConstraintViolationException exception) {

        String id = UUID.randomUUID().toString();

        ExceptionEntity exceptionEntity = ExceptionEntity.getExceptionEntity()
                .setId(id)
                .setMessage(exception.getMessage())
                .setStatus(HttpStatus.BAD_REQUEST.value());

        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            exceptionEntity.addSubException(
                    ExceptionEntity.getExceptionEntity()
                            .setId(id)
                            .setMessage(constraintViolation.getMessage())
                            .setStatus(HttpStatus.BAD_REQUEST.value())
            );
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionEntity);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionEntity> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionEntity.getExceptionEntity()
                                .setId(UUID.randomUUID().toString())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.NOT_FOUND.value())
                );
    }

    @ExceptionHandler(AlreadyExistException.class)
    public final ResponseEntity<ExceptionEntity> handleUserAlreadyExistException(AlreadyExistException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ExceptionEntity.getExceptionEntity()
                                .setId(UUID.randomUUID().toString())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.CONFLICT.value())
                );
    }

    @ExceptionHandler(TokenRefreshException.class)
    public final ResponseEntity<ExceptionEntity> handleTokenRefreshException(TokenRefreshException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionEntity.getExceptionEntity()
                                .setId(UUID.randomUUID().toString())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.BAD_REQUEST.value())
                );
    }

    @ExceptionHandler(NotActiveException.class)
    public final ResponseEntity<ExceptionEntity> handleNotActiveException(NotActiveException exception) {
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(
                        ExceptionEntity.getExceptionEntity()
                                .setId(UUID.randomUUID().toString())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.LOCKED.value())
                );
    }
}
