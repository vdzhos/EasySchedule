package com.easyschedule.exceptions.handlers;


import com.easyschedule.exceptions.lesson.NoLessonWithSuchIdFound;
import com.easyschedule.exceptions.teacher.TeacherAlreadyExistsException;
import com.easyschedule.exceptions.teacher.TeacherNotFoundException;
import com.easyschedule.exceptions.user.LoginUsedException;
import com.easyschedule.exceptions.user.UserAccessForbiddenException;
import com.easyschedule.exceptions.user.UserNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {UserAccessForbiddenException.class, LoginUsedException.class})
    public ResponseEntity<Map<String,String>> handleUserAccessForbiddenException(Exception e) {
        return makeExceptionResponseEntity(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleUserAccessForbiddenException(UserNotFoundException e) {
        return makeExceptionResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoLessonWithSuchIdFound.class})
    public ResponseEntity<Map<String,String>> handleLessonNotFoundException(NoLessonWithSuchIdFound e) {
        return makeExceptionResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TeacherNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleTeacherNotFoundException(TeacherNotFoundException e) {
        return makeExceptionResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TeacherAlreadyExistsException.class})
    public ResponseEntity<Map<String,String>> handleTeacherAlreadyExistsException(TeacherAlreadyExistsException e) {
        return makeExceptionResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class, InvalidFormatException.class})
    public ResponseEntity<Map<String,String>> handleInvalidRequestBodyException(Exception e) {
        return makeExceptionResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String,String>> makeExceptionResponseEntity(Exception e, HttpStatus httpStatus) {
        return new ResponseEntity<>(makeSimpleExceptionResponse(e), httpStatus);
    }

    private Map<String, String> makeSimpleExceptionResponse(Exception e) {
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        map.put("error", e.getMessage());
        return map;
    }
}
