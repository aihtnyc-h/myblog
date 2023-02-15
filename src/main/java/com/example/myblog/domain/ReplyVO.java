package com.example.myblog.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyVO {
    private Long rno;
    private Long bno;

    private String reply;   //댓글
    private String replyer; //작성자
    private Date replyDate; //댓글 내용
    private Date updateDate; //댓글 수정

}
