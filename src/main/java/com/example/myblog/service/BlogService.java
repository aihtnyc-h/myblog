package com.example.myblog.service;

import com.example.myblog.dto.*;
import com.example.myblog.entity.Blog;
import com.example.myblog.entity.User;
import com.example.myblog.entity.UserRoleEnum;
import com.example.myblog.jwt.JwtUtil;
import com.example.myblog.repository.BlogRepository;
import com.example.myblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
//    @Transactional
//    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
//        Blog blog = new Blog(requestDto);
//        blogRepository.save(blog);
//        return new BlogResponseDto(blog);
//    }
    // 전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<Blog> getBlog() {
        return blogRepository.findAllByOrderByModifiedAtDesc();
    }

    // 게시글 작성 API
    @Transactional(readOnly = true)
    public List<BlogResponseDto> createBlog(BlogRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<BlogResponseDto> list = new ArrayList<>();
            List<Blog> blogList;

            if (userRoleEnum == UserRoleEnum.USER) {
                // 사용자 권한이 USER일 경우
                blogList = blogRepository.findAllByUserId(user.getId());
            } else {
                blogList = blogRepository.findAll();
            }

            for (Blog blog : blogList) {
                list.add(new BlogResponseDto(blog));
            }

            return list;

        } else {
            return null;
        }
    }

    // 선택한 게시글 조회


    // 선택한 게시글 수정


    // 선택한 게시글 삭제

//    @Transactional(readOnly = true)
//    public List<Blog> getBlog() {
//        return blogRepository.findAllByOrderByModifiedAtDesc();
//    }

    //한개 조회
    @Transactional
    public Optional<Blog> getBlogs(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return blogRepository.findById(id);
    }

    @Transactional
    public Object updateBlog(Long id, BlogRequestDto requestDto) {
        if (!validatePassword(id, requestDto.getPassword())) {      //비밀번호가 같지 않을 때 사용!
            String success;
            return blogRepository.findById(id);
        }

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        blog.update(requestDto);
        return blog.getId();
    }
    //        @Transactional
//        public BlogDto<BlogMessageDto> updateBlog(Long id, BlogRequestDto requestDto) {
//            // 비밀번호 확인보다 id를 먼저 조회해야 한다 생각해서 위로 올렸다.
//            Blog blog;
//
//            try {
//                blog = blogRepository.findById(id).orElseThrow(
//                        () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//                );
//            } catch (IllegalArgumentException exception) {
//                return new  BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
//            }
//
//            if (!validatePassword(id, requestDto.getPassword())) {
//                return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호가 틀렸습니다."));
//            }
//
//            blog.update(requestDto);
//            return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 변경 성공"));
//        }
    // 게시글 삭제
    @Transactional
    public Object deleteBlog(Long id, String password) {
        if (!validatePassword(id, password)) {
            return blogRepository.findById(id);
        }
        blogRepository.deleteById(id);
        return id;
    }


//        @Transactional
//        public BlogDto<? extends Object> deleteBlog(Long id, String password) {
//            if (!validatePassword(id, password)) {
//                return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호를 다시 확인하세요."));
//            }
//            blogRepository.deleteById(id);
//            return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 삭제 성공"));
//        }

    private boolean validatePassword(Long id, String password) {
        return password.equals(blogRepository.getReferenceById(id).getPassword());
    }

}
//@Service
//@RequiredArgsConstructor
//public class BlogService {
//    private final BlogRepository blogRepository;
//    private Blog[] blog;
//
////
////    //    @Transactional(readOnly = true)
//////    public List<Blog> getBlogs() {
//////        return blogRepository.findAllByOrderByModifiedAtDesc();
//////    }
//////    @Transactional
//////    public Optional<Blog> getBlog(Long id) {
//////        Blog blog = blogRepository.findById(id).orElseThrow(
//////                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//////        );
//////        return Optional.ofNullable(blog);
//////    }
//////전체조회
////    @Transactional(readOnly = true)
////    public List<Blog> getBlog() {
////        return blogRepository.findAllByOrderByModifiedAtDesc();
////    }
////한개 조회
////    @Transactional
////    public Optional<Blog> getBlogs(Long id) {
////    Blog blog = blogRepository.findById(id).orElseThrow(
////            () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////    );
////    return blogRepository.findById(id);
////}
//////생성
////    @Transactional
////    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
////        Blog blog = new Blog(requestDto);
////        blogRepository.save(blog);
////        return new BlogResponseDto(blog);
////    }
////
//////    @Transactional(readOnly = true)
//////    public BlogRequestDto blog(Long id) {
//////        Blog blog = blogRepository.findById(id).orElseThrow(
//////                ()  -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//////        );
//////        return new BlogRequestDto();
//////    }
////    //수정
////    @Transactional
////
////    public String updateBlog(Long id, BlogRequestDto requestDto) {
////    String sucess = "true";
////    String fail = "false";
////        if (!validatePassword(id, requestDto.getPassword())) {      //비밀번호가 같지 않을 때 사용!
////            return fail;
////        }
////
////        Blog blog = blogRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////        );
////        blog.update(requestDto);
////        return sucess;
////    }
////    //삭제
////    @Transactional
////    public Object deleteBlog(Long id, String password) {
////        String sucess = "true";
////        String fail = "false";
////        if (!validatePassword(id, password)) {
////            return fail;
////        }
////        blogRepository.deleteById(id);
////        return sucess;
////    }
////    @Transactional
////    public String deleteBlog(Long id, BlogRequestDto requestDto) {
////        String sucess = "true";
////        String fail = "false";
////        if (!validatePassword(id, requestDto.getPassword())) {
////            return fail;
////        }
////        blogRepository.deleteById(id);
//////        JSONObject success = (JSONObject) password;
//////        success.equals("success");
////        return sucess;
////    }
////    @Transactional
////    public String deleteBlog(Long id, String password) throws JsonException {
////        Blog blog = blogRepository.findById(id).orElseThrow(
////                ()-> new IllegalArgumentException("아이디 없음"));
////        if (!validatePassword(BlogRequestDto)) throw new RuntimeException("비밀번호 불일치"); {      //비밀번호가 같지 않을 때 사용!
////        }
//////        if(!blog.isValid(password)) throw new RuntimeException("비밀번호 불일치");
//////        blogRepository.delete(blog);
////        JSONObject success = new JSONObject();
////        success.put("success", true);
////        return success.toString();
//
//@Service
//@RequiredArgsConstructor
//public class BlogService {
//    private final BlogRepository blogRepository;
//
////    @Transactional(readOnly = true)
////    public BlogDto<?> getBlog() {
////        List<Blog> list = blogRepository.findAllByOrderByModifiedAtDesc();
////
////        if (list.size() == 0) {
////            return new BlogDto<>("failure", new BlogMessageDto("작성된 게시글이 없습니다."));
////        }
////
////        List<BlogResponseDto> responseDtoList = new ArrayList<>();
////
////        for (Blog blog : list) {
////            responseDtoList.add(new BlogResponseDto(blog));
////        }
////
////        return new BlogDto<>("success", responseDtoList);
////    }
////        @Transactional
////    public Optional<Blog> getBlogs(Long id) {
////            Blog blog = blogRepository.findById(id).orElseThrow(
////                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////            );
////            return blogRepository.findById(id);
////        }
////    @Transactional
////    public BlogDto<BlogMessageDto> createBlog(BlogRequestDto requestDto) {
////        Blog blog = new Blog(requestDto);
////        blogRepository.save(blog);
////        return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 작성 성공했습니다."));
////    }
//
////    @Transactional(readOnly = true)
////    public BlogDto<?> getPost(Long id) {
////
////        Blog blog;
////
////        try {
////            blog = blogRepository.findById(id).orElseThrow(
////                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////            );
////        } catch (IllegalArgumentException exception) {
////            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
////        }
////
////        return new BlogDto<BlogResponseDto>("success", new BlogResponseDto(blog));
////    }
////
////    @Transactional
////    public BlogDto<BlogMessageDto> updateBlog(Long id, BlogRequestDto requestDto) {
////        // 비밀번호 확인보다 id를 먼저 조회해야 한다 생각해서 위로 올렸다.
////        Blog blog;
////
////        try {
////            blog = blogRepository.findById(id).orElseThrow(
////                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////            );
////        } catch (IllegalArgumentException exception) {
////            return new  BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
////        }
////
////        if (!validatePassword(id, requestDto.getPassword())) {
////            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호가 틀렸습니다."));
////        }
////
////        blog.update(requestDto);
////        return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 변경 성공"));
////    }
////
////    @Transactional
////    public BlogDto<? extends Object> deleteBlog(Long id, String password) {
//////        try {
//////            blogRepository.findById(id).orElseThrow(
//////                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//////            );
//////        } catch (IllegalArgumentException exception) {
//////            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
//////        }
////
////        if (!validatePassword(id, password)) {
////            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호가 틀렸습니다."));
////        }
////
////        blogRepository.deleteById(id);
////        return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 삭제 성공"));
////    }
////
////
////    private Boolean validatePassword(Long id, String password) {
////        return password.equals(blogRepository.getReferenceById(id).getPassword());
////    }
////
////
////}
//
////    @Transactional(readOnly = true)
////    public Optional<Memo> getPost(Long id) {
////        return memoRepository.findById(id);
////    }
//
////    @Transactional(readOnly = true)
////    public MemoRepository getMemo(Long id) {
////        Memo post = memoRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////        );
////
////        return new MemoResponseDto(memo);
////    }
////
////        return memo.getId();
////        memo = memoRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
////        );
////
////        if (!memo.getPassword().equals(id)) {
////            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
////
////        };
////        return Long.valueOf(memo.getPassword());
////return Long.valueOf(memo.getPassword());
//
//
////    @Transactional
////    public Long deleteMemo(Long password) {
////        Memo memo = memoRepository.findById(password).orElseThrow(
////                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
////        );
////        if (!memo.Password().equals(password)) {
////            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
////
////        }
////
////        return password;
////    }
//
//    @Transactional
//    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
//        Blog blog = new Blog(requestDto);
//        blogRepository.save(blog);
//        return new BlogResponseDto(blog);
//    }
//
//
//    @Transactional(readOnly = true)
//    public List<Blog> getBlog() {
//        return blogRepository.findAllByOrderByModifiedAtDesc();
//    }
//
//    //한개 조회
//    @Transactional
//    public Optional<Blog> getBlogs(Long id) {
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        return blogRepository.findById(id);
//    }
//// 게시물 수정
////    @Transactional
////    public Long updateBlog(Long id, BlogRequestDto requestDto) {
////        if (!validatePassword(id, requestDto.getPassword())) {      //비밀번호가 같지 않을 때 사용!
////            return -999L;
////        }
////
////        Blog blog = blogRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
////        );
////        blog.update(requestDto);
////        return blog.getId();
////    }
//    @Transactional
//    public BlogDto<BlogMessageDto> updateBlog(Long id, BlogRequestDto requestDto) {
//        // 비밀번호 확인보다 id를 먼저 조회해야 한다 생각해서 위로 올렸다.
//        Blog blog;
//
//        try {
//            blog = blogRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//            );
//        } catch (IllegalArgumentException exception) {
//            return new  BlogDto<BlogMessageDto>("failure", new BlogMessageDto("게시글이 존재하지 않습니다."));
//        }
//
//        if (!validatePassword(id, requestDto.getPassword())) {
//            return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호가 틀렸습니다."));
//        }
//
//        blog.update(requestDto);
//        return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 변경 성공"));
//    }
//
//
//    // 게시글 삭제
////    @Transactional
////    public Long deleteBlog(Long id, String password) {
////        if (!validatePassword(id, password)) {
////            return -999L;
////        }
////
////        blogRepository.deleteById(id);
////        return id;
////    }
////    private Boolean validatePassword(Long id, String password) {
////        return password.equals(blogRepository.getReferenceById(id).getPassword());
////    }
//
//  // 게시글 삭제
//     @Transactional
//     public BlogDto<? extends Object> deleteBlog(Long id, String password) {
//     if (!validatePassword(id, password)) {
//         return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호를 다시 확인하세요."));
//     }
//     blogRepository.deleteById(id);
//     return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 삭제 성공"));
// }
//
//    private boolean validatePassword(Long id, String password) {
//        return password.equals(blogRepository.getReferenceById(id).getPassword());
//    }
//
//
//}
