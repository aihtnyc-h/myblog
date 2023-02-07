package com.example.myblog.controller;

import com.example.myblog.dto.BlogRequestDto;
import com.example.myblog.dto.BlogResponseDto;
import com.example.myblog.entity.Blog;
import com.example.myblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BlogController {

    private final BlogService blogService;

    //게시글 조회하기 서버
    @GetMapping("/")
    public List<Blog> getBlog() {
        return blogService.getBlog();
    }
    // 게시글 생성하기 서버
    @PostMapping("/api/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    //게시글 한개 조회하기 서버
    @GetMapping("/api/blog/{id}")
    public Optional<Blog> getBlogs(@PathVariable Long id){
        return blogService.getBlogs(id);
    }

    //게시글 변경하기 서버
    @PutMapping("/api/blog/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.updateBlog(id, requestDto);
    }

    //게시글 삭제하기 서버
    @DeleteMapping("/api/blog/{id}")
    public Long deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.deleteBlog(id, requestDto.getPassword());
    }
}
