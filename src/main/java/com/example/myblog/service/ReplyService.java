package com.example.myblog.service;

import com.example.myblog.domain.ReplyVO;

import org.hibernate.Criteria;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyService {
    public int register(ReplyVO vo);
    public ReplyVO get(Long rno);
    public int remove(Long rno);
    public int modify(ReplyVO reply);
    public List<ReplyVO> getList(
            @Param("cri") Criteria cri,
            @Param("bno") Long bno);

}
