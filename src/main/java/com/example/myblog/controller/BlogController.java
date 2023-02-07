package com.example.myblog.controller;

import com.example.myblog.dto.BlogRequestDto;
import com.example.myblog.entity.Blog;
import com.example.myblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BlogController {

    private final BlogService blogService;

    // 메인페이지
    @GetMapping("/")
    public List<Blog> getBlog() {
        return blogService.getBlog();
    }
    // 게시글 생성하기 서버
    @PostMapping("/api/blog")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    //게시글 조회하기 서버
    @GetMapping("/api/blog")
    public List<Blog> getBlog(@PathVariable Long id) {
        return blogService.getBlog();
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
