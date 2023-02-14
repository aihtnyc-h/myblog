package com.example.myblog.repository;

import com.example.myblog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {  // JpaRepository 추후에 만들고 적용시키기!
    List<Blog> findAllByOrderByModifiedAtDesc();

    List<Blog> findAllByUserName(String userName);

    Optional<Blog> findByIdAndUserId(Long id, Long userId);
}
