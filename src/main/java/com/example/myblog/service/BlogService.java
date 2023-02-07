package com.example.myblog.service;

import com.example.myblog.dto.BlogRequestDto;
import com.example.myblog.dto.BlogResponseDto;
import com.example.myblog.entity.Blog;
import com.example.myblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return new BlogResponseDto(blog);
    }


    @Transactional(readOnly = true)
    public List<Blog> getBlog() {
        return blogRepository.findAllByOrderByModifiedAtDesc();
    }

//한개 조회
@Transactional
public Optional<Blog> getBlogs(Long id) {
    Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
    );
    return blogRepository.findById(id);
}

    @Transactional
    public Long updateBlog(Long id, BlogRequestDto requestDto) {
        if (!validatePassword(id, requestDto.getPassword())) {      //비밀번호가 같지 않을 때 사용!
            return -999L;
        }

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        blog.update(requestDto);
        return blog.getId();
    }

    @Transactional
    public Long deleteBlog(Long id, String password) {
        if (!validatePassword(id, password)) {
            return -999L;
        }

        blogRepository.deleteById(id);
        return id;
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
