package com.example.myblog.service;

import com.example.myblog.dto.BlogDto;
import com.example.myblog.dto.BlogMessageDto;
import com.example.myblog.dto.BlogRequestDto;
import com.example.myblog.dto.BlogResponseDto;
import com.example.myblog.entity.Blog;
import com.example.myblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@Service
//@RequiredArgsConstructor
//public class BlogService {
//    private final BlogRepository blogRepository;
//    private Blog[] blog;
//
//
//    //    @Transactional(readOnly = true)
////    public List<Blog> getBlogs() {
////        return blogRepository.findAllByOrderByModifiedAtDesc();
////    }
////    @Transactional
////    public Optional<Blog> getBlog(Long id) {
////        Blog blog = blogRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////        );
////        return Optional.ofNullable(blog);
////    }
////전체조회
//    @Transactional(readOnly = true)
//    public List<Blog> getBlog() {
//        return blogRepository.findAllByOrderByModifiedAtDesc();
//    }
////한개 조회
//    @Transactional
//    public Optional<Blog> getBlogs(Long id) {
//    Blog blog = blogRepository.findById(id).orElseThrow(
//            () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//    );
//    return blogRepository.findById(id);
//}
////생성
//    @Transactional
//    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
//        Blog blog = new Blog(requestDto);
//        blogRepository.save(blog);
//        return new BlogResponseDto(blog);
//    }
//
////    @Transactional(readOnly = true)
////    public BlogRequestDto blog(Long id) {
////        Blog blog = blogRepository.findById(id).orElseThrow(
////                ()  -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////        );
////        return new BlogRequestDto();
////    }
//    //수정
//    @Transactional
//
//    public String updateBlog(Long id, BlogRequestDto requestDto) {
//    String sucess = "true";
//    String fail = "false";
//        if (!validatePassword(id, requestDto.getPassword())) {      //비밀번호가 같지 않을 때 사용!
//            return fail;
//        }
//
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        blog.update(requestDto);
//        return sucess;
//    }
//    //삭제
//    @Transactional
//    public Object deleteBlog(Long id, String password) {
//        String sucess = "true";
//        String fail = "false";
//        if (!validatePassword(id, password)) {
//            return fail;
//        }
//        blogRepository.deleteById(id);
//        return sucess;
//    }
//    @Transactional
//    public String deleteBlog(Long id, BlogRequestDto requestDto) {
//        String sucess = "true";
//        String fail = "false";
//        if (!validatePassword(id, requestDto.getPassword())) {
//            return fail;
//        }
//        blogRepository.deleteById(id);
////        JSONObject success = (JSONObject) password;
////        success.equals("success");
//        return sucess;
//    }
//    @Transactional
//    public String deleteBlog(Long id, String password) throws JsonException {
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                ()-> new IllegalArgumentException("아이디 없음"));
//        if (!validatePassword(BlogRequestDto)) throw new RuntimeException("비밀번호 불일치"); {      //비밀번호가 같지 않을 때 사용!
//        }
////        if(!blog.isValid(password)) throw new RuntimeException("비밀번호 불일치");
////        blogRepository.delete(blog);
//        JSONObject success = new JSONObject();
//        success.put("success", true);
//        return success.toString();

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional(readOnly = true)
    public BlogDto<?> getBlog() {
        List<Blog> list = blogRepository.findAllByOrderByModifiedAtDesc();

        if (list.size() == 0) {
            return new BlogDto<>("failure", new BlogMessageDto("작성된 게시글이 없습니다."));
        }

        List<BlogResponseDto> responseDtoList = new ArrayList<>();

        for (Blog blog : list) {
            responseDtoList.add(new BlogResponseDto(blog));
        }

        return new BlogDto<>("success", responseDtoList);
    }

    @Transactional
    public BlogDto<BlogMessageDto> createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return new BlogDto<>("success", new BlogMessageDto("게시글 작성 성공했습니다."));
    }

    @Transactional(readOnly = true)
    public BlogDto<?> getPost(Long id) {
        // DTO<T> 만들어서 Exception 핸들링 할 것
        Blog blog;

        try {
            blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
        } catch (IllegalArgumentException exception) {
            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
        }

        return new BlogDto<BlogResponseDto>("success", new BlogResponseDto(blog));
    }

    @Transactional
    public BlogDto<BlogMessageDto> updateBlog(Long id, BlogRequestDto requestDto) {
        // 비밀번호 확인보다 id를 먼저 조회해야 한다 생각해서 위로 올렸다.
        Blog blog;

        try {
            blog = blogRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
        } catch (IllegalArgumentException exception) {
            return new  BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
        }

        if (!validatePassword(id, requestDto.getPassword())) {
            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호가 틀렸습니다."));
        }

        blog.update(requestDto);
        return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 변경 성공"));
    }

    @Transactional
    public BlogDto<? extends Object> deleteBlog(Long id, String password) {
//        try {
//            blogRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//            );
//        } catch (IllegalArgumentException exception) {
//            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
//        }

        if (!validatePassword(id, password)) {
            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호가 틀렸습니다."));
        }

        blogRepository.deleteById(id);
        return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 삭제 성공"));
    }


    private Boolean validatePassword(Long id, String password) {
        return password.equals(blogRepository.getReferenceById(id).getPassword());
    }


}

//    @Transactional(readOnly = true)
//    public Optional<Memo> getPost(Long id) {
//        return memoRepository.findById(id);
//    }

//    @Transactional(readOnly = true)
//    public MemoRepository getMemo(Long id) {
//        Memo post = memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//
//        return new MemoResponseDto(memo);
//    }
//
//        return memo.getId();
//        memo = memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
//        );
//
//        if (!memo.getPassword().equals(id)) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//
//        };
//        return Long.valueOf(memo.getPassword());
//return Long.valueOf(memo.getPassword());


//    @Transactional
//    public Long deleteMemo(Long password) {
//        Memo memo = memoRepository.findById(password).orElseThrow(
//                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
//        );
//        if (!memo.Password().equals(password)) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//
//        }
//
//        return password;
//    }
