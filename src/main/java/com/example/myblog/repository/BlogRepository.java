package com.example.myblog.repository;

import com.example.myblog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {  // JpaRepository 추후에 만들고 적용시키기!
    List<Blog> findAllByOrderByModifiedAtDesc();

    List<Blog> findAllByUserId(Long id);
}
