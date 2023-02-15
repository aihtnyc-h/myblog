package com.example.myblog.service;

import com.example.myblog.domain.ReplyVO;
import com.example.myblog.mapper.ReplyMapper;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public abstract class ReplyServiceImpl implements ReplyService{
    @Setter(onMethod_ =@Autowired)
    private ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        log.info("register...." + vo);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        log.info("get...." + rno);
        return mapper.read(rno);
    }

    @Override
    public int remove(Long rno) {
        log.info("remove....." + rno);
        return mapper.delete(rno);
    }

    @Override
    public int modify(ReplyVO reply) {
        log.info("modify....." + reply);
        return mapper.update(reply);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {
        log.info("get Reply list " + bno);
        return mapper.getListWithPaging(cri, bno);
    }

}