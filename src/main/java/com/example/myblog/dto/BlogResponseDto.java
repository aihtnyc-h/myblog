package com.example.myblog.dto;

import com.example.myblog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BlogResponseDto {
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    //private LocalDateTime modifiedAt;

    public List<BlogResponseDto> blogResponseDto = new ArrayList<>();

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitlename();
        this.contents = blog.getContents();
        this.username = blog.getUsername();
        this.createdAt = blog.getCreatedAt();
       // this.modifiedAt = blog.getModifiedAt();
    }
//    public BlogResponseDto(Blog blog, List<BlogResponseDto> blogResponseDto) {
//        this.titlename = blog.getTitlename();
//        this.contents = blog.getContents();
//        this.username = blog.getUsername();
//        this.createdAt = blog.getCreatedAt();
//        //this.modifiedAt = blog.getModifiedAt();
//    }
}
