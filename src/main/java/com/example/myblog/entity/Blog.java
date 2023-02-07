package com.example.myblog.entity;

import com.example.myblog.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 기본키 설정!
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private String titlename;
    @Column(nullable = false)
    private String password;

    public Blog(BlogRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.titlename = requestDto.getTitlename();
        this.password = requestDto.getPassword();

    }
    public void update(BlogRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.titlename = requestDto.getTitlename();
        this.password = requestDto.getPassword();

    }

//    public Post(){
//        this.username =
//    }
}
