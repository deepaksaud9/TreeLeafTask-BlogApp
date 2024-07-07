package com.blogapp.blog.service.serviceImpl;


import com.blogapp.blog.model.Post;
import com.blogapp.blog.repository.PostRepository;
import com.blogapp.blog.service.PostService;
import com.blogapp.exception.BlogExceptionClass;
import com.blogapp.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;

    }


    @Override
    public Post savePost(Post post) {
        if (post.getTitle().equals("")){
            throw new BlogExceptionClass("Title required for post");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Long bloggerId = userDetails.getId();

        post.setBloggerId(bloggerId);
        post.setPostDate(new Date());
        return postRepository.save(post);

    }

    @Override
    public Post updatePost(long postId, Post post) {

        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            optionalPost.get().setTitle(post.getTitle());
            optionalPost.get().setPostDescription(post.getPostDescription());
            optionalPost.get().setUpdateDate(new Date());
            optionalPost.get().setThumbnailImage(post.getThumbnailImage());
            postRepository.save(optionalPost.get());
            return optionalPost.get();
        }else {
            throw new BlogExceptionClass("PostId not found");
        }
    }

    @Override
    public String deletePost(long postId) {

        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            optionalPost.get().setStatus(false);
            postRepository.save(optionalPost.get());
            return "Your post deleted successfully.";
        }else {
            throw new BlogExceptionClass("PostId not found");

        }
    }

    @Override
    public List<Post> findAllActivePost() {
        return postRepository.findByStatus(true);
    }
}
