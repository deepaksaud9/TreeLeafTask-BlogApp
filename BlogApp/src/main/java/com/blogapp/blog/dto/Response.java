package com.blogapp.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int statusCode;
    private Object payload;

    public Response(Object payload, int statusCode){
        this.payload = payload;
        this.statusCode = statusCode;
    }

}
