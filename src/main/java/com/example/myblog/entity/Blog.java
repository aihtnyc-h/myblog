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
    @GeneratedValue(strategy = GenerationType.AUTO)     // AUTO말고 다른 타입들 공부하기
    private Long id; // 기본키 설정!
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private String titleName;
    @Column(nullable = false)
    private Long userId;
    // userId에서
    @Column(nullable = false)
    private String password;


    public Blog(BlogRequestDto requestDto, Long userId) {
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.titleName = requestDto.getTitleName();
        this.password = requestDto.getPassword();
        this.userId = userId;

    }
    public void update(BlogRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.titleName = requestDto.getTitleName();
        this.password = requestDto.getPassword();

    }



//    public Post(){
//        this.username =
//    }
}
