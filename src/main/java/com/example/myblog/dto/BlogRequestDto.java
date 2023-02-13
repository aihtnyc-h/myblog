package com.example.myblog.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

@Getter
public class BlogRequestDto {
    private String userName;
    private String contents;
    private String titleName;
    private String password;

}

