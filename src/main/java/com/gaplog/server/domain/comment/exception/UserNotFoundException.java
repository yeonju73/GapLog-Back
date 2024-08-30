package com.gaplog.server.domain.comment.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId){
        super("User not found with id: " + userId);
    }
}
