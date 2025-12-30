package com.school.service;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) { super(message); }
}

