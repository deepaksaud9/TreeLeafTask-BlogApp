package com.blogapp.blog.controller;

import com.blogapp.blog.dto.CommentDto;
import com.blogapp.blog.dto.Response;
import com.blogapp.blog.model.Comment;
import com.blogapp.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/saveComment")
    public ResponseEntity<Response> createComment(@RequestBody CommentDto commentdto){

        Comment comment = commentService.saveComment(commentdto);
        Response response = new Response(comment,HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateComment/{commentId}")
    public ResponseEntity<Response> updateComment(@PathVariable long commentId, @RequestBody CommentDto commentDto){

        Comment comment = commentService.updateComment(commentId,commentDto);
        Response response = new Response(comment,HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment delete Successfully",HttpStatus.OK);
    }

    @GetMapping("/findAllComments")
    public ResponseEntity<List<Response>> getAllComment(){

        List<Comment> comments = commentService.getAllComments();
        List<Response> response = comments.stream()
                                    .map(comment -> new Response(comment, HttpStatus.OK.value())).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
