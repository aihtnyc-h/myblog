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
import org.json.JSONObject;
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



    //  게시글 1 조회
    @Transactional (readOnly = true)
    public BlogResponseDto getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.") //DB에 존재하지 않으면
        );
        BlogResponseDto BlogResponseDto = new BlogResponseDto(blog);
        return BlogResponseDto;
    }
//        @Transactional(readOnly = true)
//    public List<Blog> getBlog() {
//
//        return blogRepository.findAllByOrderByModifiedAtDesc();
//    }
//    @Transactional(readOnly = true)
//
//    public List<BlogResponseDto> getBlog() {
//        List <Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc(); // 리스트 형태로 내림차순 정리
//        List <BlogResponseDto> blogResponseDtoList = new ArrayList<>(); // 리스트 생성
//        for (Blog blog : blogList) {
//            BlogResponseDto tmp = new BlogResponseDto(blog);
//            blogResponseDtoList.add(tmp);
//        }
//        return blogResponseDtoList;
//    }
//    public List<BlogResponseDto> getBlog() {
//        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
//        List<Blog> blogList;
//
//        blogList = blogRepository.findAll();
//
//        for (Blog i : blogList) {
//            list.add(new BlogResponseDto(i));
//        }
//        return list;
//    }

    // 게시글 전체 조회하기 (유저 이름을 기준! -> 기존 id와 user의 username을 같게 만들어주기!
    @Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogs() {
        List<BlogResponseDto> list = new ArrayList<>();
        List<Blog> blogList;
        blogList = blogRepository.findAll();
        for (Blog i : blogList) {
            list.add(new BlogResponseDto((i)));
        }
        return list;
    }
    // 선택한 게시글 조회   500에 아이디가 존재하지 않습니다.

    //    public Optional<Blog> getBlogs(Long id) {
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        return blogRepository.findById(id);
//    }
    // 게시글 작성 API   // 성공!
    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto requestDto, HttpServletRequest request) {
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

            // 요청받은 DTO로 DB에 저잘할 객체 만들기
            Blog blog = blogRepository.saveAndFlush(new Blog(requestDto, user.getId()));
            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }
// List 버전
//            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
//            UserRoleEnum userRoleEnum = user.getRole();
//            System.out.println("role = " + userRoleEnum);
//
//            List<BlogResponseDto> list = new ArrayList<>();
//            List<Blog> blogList;
//
//            if (userRoleEnum == UserRoleEnum.USER) {
//                // 사용자 권한이 USER일 경우
//                blogList = blogRepository.findAllByUserName(user.getUsername());
//            } else {
//                blogList = blogRepository.findAll();
//            }
//
//            for (Blog blog : blogList) {
//                list.add(new BlogResponseDto(blog));
//            }
//
//            return list;
//
//        } else {
//            return null;
//        }
//        }
//    }

    // 선택한 게시글 수정
    // - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
    //- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
//    @Transactional
//    public Object updateBlog(Long id, BlogRequestDto requestDto) {
//        if (!validatePassword(id, requestDto.getPassword())) {      //비밀번호가 같지 않을 때 사용!
//            String success;
//            return blogRepository.findById(id);
//        }
//
//        Blog blog = blogRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        blog.update(requestDto);
//        return blog.getId();
//    }
    // 게시글 수정하기
    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto, HttpServletRequest request) {  // @RequestBody 제거함
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 수정 가능
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
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );

            blog.update(requestDto);
            BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

            return blogResponseDto;
        }
        return null;
    }


    // List로 사용했을 때
    // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
//            UserRoleEnum userRoleEnum = user.getRole();
//            System.out.println("role = " + userRoleEnum);
//
//            List<BlogResponseDto> list = new ArrayList<>();
//            List<Blog> blogList;
//
//            if (userRoleEnum == UserRoleEnum.USER) {
//                // 사용자 권한이 USER일 경우
//                blogList = blogRepository.findAllByUserName(user.getUsername());
//            } else {
//                blogList = blogRepository.findAll();
//            }
//
//            for (Blog blog : blogList) {
//                list.add(new BlogResponseDto(blog));
//            }
//
//            return list;
//
//        } else {
//            return null;
//        }
//    }
    // 선택한 게시글 삭제
//    @Transactional
//     public BlogDto<? extends Object> deleteBlog(Long id, String password) {
//     if (!validatePassword(id, password)) {
//         return new BlogDto<BlogMessageDto>("failure", new BlogMessageDto("비밀번호를 다시 확인하세요."));
//     }
//     blogRepository.deleteById(id);
//     return new BlogDto<BlogMessageDto>("success", new BlogMessageDto("게시글 삭제 성공"));
// }
    //게시글 삭제
    @Transactional
    public String deleteBlog(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );
            blogRepository.deleteById(id);
            JSONObject success = new JSONObject();
            success.put("success", true);
            return success.toString();
        }
        return null;
    }
}