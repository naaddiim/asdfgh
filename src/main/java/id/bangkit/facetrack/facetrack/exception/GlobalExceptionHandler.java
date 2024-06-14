package id.bangkit.facetrack.facetrack.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EmailNotFoundException.class)
    public Object handleEmailNotFoundException(EmailNotFoundException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SaveImageException.class)
    public Object handleSaveImageException(SaveImageException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", "File too large, maximum 500KB");
        return body;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public Object handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", "Credentials is not correct");
        return body;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ExpiredJwtException.class)
    public Object handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", "Token is Expired");
        return body;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = MalformedJwtException.class)
    public Object handleMalformedJwtException(MalformedJwtException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", "Token is not right");
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put("errors", errors);
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = EmailUnavailableException.class)
    public Object handleEmailUnavailableException(EmailUnavailableException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    public Object handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ProgramNotFoundException.class)
    public Object handleProgramNotFoundException(ProgramNotFoundException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = FileUploadNotFoundException.class)
    public Object handleFileUploadNotFoundException(FileUploadNotFoundException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = FileNameUnavailableException.class)
    public Object handleFileNameUnavailableException(FileNameUnavailableException ex, HttpServletRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("errors", ex.getMessage());
        return body;
    }


}

