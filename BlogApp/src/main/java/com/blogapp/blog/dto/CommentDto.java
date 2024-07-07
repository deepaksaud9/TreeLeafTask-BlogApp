package com.blogapp.blog.dto;

import com.blogapp.blog.model.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentDescription;
    private long postId;
    private long userId;
    private Date commentDate;
    private Date updateDate;

}
