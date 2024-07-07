package com.blogapp.blog.service.serviceImpl;

import com.blogapp.blog.dto.CommentDto;
import com.blogapp.blog.model.Comment;
import com.blogapp.blog.repository.CommentRepository;
import com.blogapp.blog.repository.PostRepository;
import com.blogapp.blog.service.CommentService;
import com.blogapp.exception.BlogExceptionClass;
import com.blogapp.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment saveComment(CommentDto commentDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        long userId = userDetails.getId();

        if (commentDto.getCommentDescription().equals("")){
            throw new BlogExceptionClass("Comment Should not be null");
        }

        Comment comment = new Comment();
        comment.setCommentDescription(commentDto.getCommentDescription());
        comment.setCommentDate(new Date());
        comment.setUserId(userId);
        comment.setPost(postRepository.findById(commentDto.getPostId()).get());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(long commentId, CommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()){

            optionalComment.get().setUpdateDate(new Date());
            optionalComment.get().setCommentDescription(commentDto.getCommentDescription());
            commentRepository.save(optionalComment.get());
            return optionalComment.get();
        }else {
            throw new BlogExceptionClass("CommentId not found");
        }
    }

    @Override
    public String deleteComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()){
            commentRepository.delete(optionalComment.get());
            return "your comment deleted Successfully";
        }else {
            throw new BlogExceptionClass("CommentId not found");
        }
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
