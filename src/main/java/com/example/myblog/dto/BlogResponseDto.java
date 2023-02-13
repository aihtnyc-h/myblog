package com.example.myblog.dto;
import com.example.myblog.entity.Blog;
import lombok.Getter;


import java.time.LocalDateTime;


@Getter
public class BlogResponseDto {
//    @ResponseBody
//    public String password(@RequestParam("password")String password){
//     String pw = new String();
//     password.toCharArray();
//     return pw;
//    }

    private String title;
    private String contents;
    private String userName;
    private String password;
    private LocalDateTime createdAt;



    //private LocalDateTime modifiedAt;

//    public List<BlogResponseDto> blogResponseDto = new ArrayList<>();

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitleName();
        this.contents = blog.getContents();
        this.userName = blog.getUserName();
        this.password = blog.getPassword();
        this.createdAt = blog.getCreatedAt();

        // this.modifiedAt = blog.getModifiedAt();
    }
}