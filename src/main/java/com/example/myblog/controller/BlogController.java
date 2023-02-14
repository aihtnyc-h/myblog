package com.example.myblog.controller;

import com.example.myblog.dto.BlogRequestDto;
import com.example.myblog.dto.BlogResponseDto;
import com.example.myblog.entity.Blog;
import com.example.myblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class BlogController {


    private final BlogService blogService;

    //게시글 조회하기 서버
    @GetMapping("/")
    public List<BlogResponseDto> getBlog() {
        return blogService.getBlog();
    }

    // 게시글 생성하기 서버
    @PostMapping("/api/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.createBlog(requestDto, request);
    }
//    public List<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
//        return blogService.createBlog(requestDto, request);        //변수명사용하기 + 필드명 requestDto.getContents() -> 유저네임이 제목아름으로 들어갈 수 있기 때문에 대부분 통으로 보내준다.  // 카멜 케이스 단어랑 단어를 이어줄때, 뒤에오는 글자는 대문자       //왜 빨간줄이 뜨는거니..?
//    }
// 컨트롤러의 리턴값 서비스의 리턴값과 같아야 한다. -> 서비스에서 리턴값이 list였기 때문에 컨트롤러 타입도 같이 만들어야한다.
   //게시글 한개 조회하기 서버
    @GetMapping("/api/blog/members/{id}")
    public List<BlogResponseDto> getBlogs(@RequestBody BlogRequestDto requestDto, HttpServletRequest request){
        return blogService.getBlogs(requestDto, request);
    }
//    public Optional<Blog> getBlogs(@PathVariable Long id) {
//        return blogService.getBlogs(id);
//    }

    //게시글 변경하기 서버
    @PutMapping("/api/blog/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request){
        return blogService.update(id, requestDto, request);
    }
//    public List<BlogResponseDto> updateBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
//        return blogService.updateBlog(requestDto, request);
//    }
    //게시글 변경하기 서버
//    @PutMapping("/api/blog/{id}")
//    public BlogDto<BlogMessageDto> updatePutBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//        return blogService.updateBlog(id, requestDto);
//        return ResponseUtil.SUCCESS("유저를 추가하였습니다.", null);
//    }

    //게시글 삭제하기 서버
    @DeleteMapping("/api/blog/{id}")
    public Long deleteBlog(@PathVariable long id, HttpServletRequest request){
        return blogService.deleteBlog(id, request);
    }
//    public List<BlogResponseDto> deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
//        return blogService.deleteBlog(requestDto, request);
//    }

    // 게시글 삭제하기 서버
//    @DeleteMapping("/api/blog/{id}")
//    public BlogDto<?> deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//        return blogService.deleteBlog(id, requestDto.getPassword());
//    }
    // 커밋 푸쉬 연습하자
}
    //메인페이지
//    @GetMapping("/")
//    public BlogDto<?> getBlog() {
//
//        return blogService.getBlog();
////        return (List<Blog>) ResponseUtil.SUCCESS("조회에 성공하였습니다.", UserList);
//    }
    //게시글 조회하기 서버
//    @GetMapping("/api/blog")
//    public List<Blog> getBlog(@PathVariable Long id) {
//        return blogService.getBlog();
//    }
    //게시글 한개 조회하기 서버
//    @GetMapping("/api/blog/{id}")
//    public Optional<Blog> getBlogs(@PathVariable Long id){
//
//        return blogService.getBlogs(id);
////        return ResponseUtil.SUCCESS("조회에 성공하였습니다.", UserList);
//    }
//    // 게시글 생성하기 서버
//    @PostMapping("/api/blog")
//    public BlogDto<BlogMessageDto> createBlog(@RequestBody BlogRequestDto requestDto) {
//        return blogService.createBlog(requestDto);
//    }
//
//    //게시글 변경하기 서버
//    @PutMapping("/api/blog/{id}")
//    public BlogDto<BlogMessageDto> updatePutBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//        return blogService.updateBlog(id, requestDto);
////        return ResponseUtil.SUCCESS("유저를 추가하였습니다.", null);
//    }
//    //게시글 삭제하기
//    //게시글 삭제하기 서버
//    @DeleteMapping("/api/blog/{id}")
//    public BlogDto<?> deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//       return blogService.deleteBlog(id, requestDto.getPassword());
////        return ResponseUtil.SUCCESS("유저를 추가하였습니다.", null);
//    }
//}
//    @DeleteMapping("/api/blog/{id}")
//    public String deleteBlog(@PathVariable Long id, @RequestBody String password) {
//        return blogService.deleteBlog(id,password);
//    }
//    @DeleteMapping("/api/blog/{id}")
//    public String deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) throws JsonEOFException {
//        return blogService.deleteBlog(id, requestDto.getPassword());
//    }

//    //게시글 조회하기 서버
//    @GetMapping("/")
//    public List<Blog> getBlog() {
//        return blogService.getBlog();
//    }
//
//    // 게시글 생성하기 서버
//    @PostMapping("/api/blog")
//    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
//        return blogService.createBlog(requestDto);
//    }
//
//    //게시글 한개 조회하기 서버
//    @GetMapping("/api/blog/{id}")
//    public Optional<Blog> getBlogs(@PathVariable Long id) {
//        return blogService.getBlogs(id);
//    }
//
//    //게시글 변경하기 서버
////    @PutMapping("/api/blog/{id}")
////    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
////        return blogService.updateBlog(id, requestDto);
////    }
//        //게시글 변경하기 서버
//    @PutMapping("/api/blog/{id}")
//    public BlogDto<BlogMessageDto> updatePutBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//        return blogService.updateBlog(id, requestDto);
////        return ResponseUtil.SUCCESS("유저를 추가하였습니다.", null);
//    }
//
//    //게시글 삭제하기 서버
////    @DeleteMapping("/api/blog/{id}")
////    public Long deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
////        return blogService.deleteBlog(id, requestDto.getPassword());
////    }
//    // 게시글 삭제하기 서버
//    @DeleteMapping("/api/blog/{id}")
//    public BlogDto<?> deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
//        return blogService.deleteBlog(id, requestDto.getPassword());
//    }
//}