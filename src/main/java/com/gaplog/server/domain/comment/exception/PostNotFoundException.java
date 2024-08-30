package com.gaplog.server.domain.comment.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId){
        super("Post not found with id: " + postId);
    }
}
