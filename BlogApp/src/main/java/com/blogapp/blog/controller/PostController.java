package com.blogapp.blog.controller;

import com.blogapp.blog.dto.Response;
import com.blogapp.blog.model.Post;
import com.blogapp.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/savePost")
    public ResponseEntity<Response> createPost(@RequestBody Post post){

        Post savedPost = postService.savePost(post);
        Response response = new Response(savedPost, HttpStatus.OK.value());

       return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<Response> updatePost(@PathVariable long postId, @RequestBody Post post){
        Post updatedPost = postService.updatePost(postId,post);
        Response response = new Response(updatedPost, HttpStatus.OK.value());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/findAllPost")
    public ResponseEntity<List<Response> >getAllPost(){
        List<Post> posts = postService.findAllActivePost();
        List<Response> response = posts.stream()
                                    .map(post -> new Response( post, HttpStatus.OK.value())).collect(Collectors.toList());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/deletePost/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId){

        return new ResponseEntity<>(postService.deletePost(postId),HttpStatus.OK);
    }

}
