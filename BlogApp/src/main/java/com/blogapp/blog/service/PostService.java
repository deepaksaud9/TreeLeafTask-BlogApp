package com.blogapp.blog.service;

import com.blogapp.blog.model.Post;

import java.util.List;

public interface PostService {

    public Post savePost(Post post);
    public Post updatePost(long postId, Post post);
    public String deletePost(long postId);
    public List<Post> findAllActivePost();
}
