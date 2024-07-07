package com.blogapp.blog.service;

import com.blogapp.blog.dto.CommentDto;
import com.blogapp.blog.model.Comment;

import java.util.List;

public interface CommentService {

    public Comment saveComment(CommentDto comment);
    public Comment updateComment(long commentId, CommentDto commentDto);
    public String deleteComment(long commentId);
    public List<Comment> getAllComments();
}
