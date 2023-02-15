package com.example.myblog.mapper;

import org.hibernate.Criteria;
import org.springframework.data.repository.query.Param;
import com.example.myblog.domain.ReplyVO;

import java.util.List;



public interface ReplyMapper {
    public int insert(ReplyVO vo);

    public ReplyVO read(Long rno);

    public int delete(Long rno);    //게시글 삭제

    public int update(ReplyVO reply);   // 게시글 등록

    public List<ReplyVO> getListWithPaging(
            @Param("cri") Criteria cri,
            @Param("bno") Long bno);
    // 페이지 정보와 게시물 번호를 전달.
}
