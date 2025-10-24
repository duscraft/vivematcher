package com.duscraft.vivematcher.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError handleNoSuchElementException(NoSuchElementException e) {
    return new ApiError("Not Found", e.getMessage() != null ? e.getMessage() : "The requested resource was not found");
  }
}
