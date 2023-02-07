package com.example.myblog.dto;

import com.example.myblog.entity.Blog;
import lombok.Getter;

@Getter
public class BlogResponseDto {
    private String title;
    private String contents;
    private String username;

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitlename();
        this.contents = blog.getContents();
        this.username = blog.getUsername();
    }
}
