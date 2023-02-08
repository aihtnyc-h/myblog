package com.example.myblog.controller;

import com.example.myblog.dto.BlogDto;
import com.example.myblog.dto.BlogRequestDto;
import com.example.myblog.service.BlogService;
import com.example.myblog.dto.BlogMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BlogController {


    private final BlogService blogService;
    //메인페이지
    @GetMapping("/")
    public BlogDto<?> getBlog() {

        return blogService.getBlog();
//        return (List<Blog>) ResponseUtil.SUCCESS("조회에 성공하였습니다.", UserList);
    }
    //게시글 조회하기 서버
//    @GetMapping("/api/blog")
//    public List<Blog> getBlog(@PathVariable Long id) {
//        return blogService.getBlog();
//    }
    //게시글 한개 조회하기 서버
    @GetMapping("/api/blog/{id}")
    public BlogDto<?> getBlog(@PathVariable Long id){

        return blogService.getBlog();
//        return ResponseUtil.SUCCESS("조회에 성공하였습니다.", UserList);
    }
    // 게시글 생성하기 서버
    @PostMapping("/api/blog")
    public BlogDto<BlogMessageDto> createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    //게시글 변경하기 서버
    @PutMapping("/api/blog/{id}")
    public BlogDto<BlogMessageDto> updatePutBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.updateBlog(id, requestDto);
//        return ResponseUtil.SUCCESS("유저를 추가하였습니다.", null);
    }
    //게시글 삭제하기
    //게시글 삭제하기 서버
    @DeleteMapping("/api/blog/{id}")
    public BlogDto<?> deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
       return blogService.deleteBlog(id, requestDto.getPassword());
//        return ResponseUtil.SUCCESS("유저를 추가하였습니다.", null);
    }
}
//    @DeleteMapping("/api/blog/{id}")
//    public String deleteBlog(@PathVariable Long id, @RequestBody String password) {
//        return blogService.deleteBlog(id,password);
//    }
//    @DeleteMapping("/api/blog/{id}")
//    public String deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) throws JsonEOFException {
//        return blogService.deleteBlog(id, requestDto.getPassword());
//    }


