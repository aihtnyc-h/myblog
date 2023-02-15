package com.example.myblog.controller;

import com.example.myblog.domain.ReplyVO;
import com.example.myblog.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/replies/")
@Log4j2
@AllArgsConstructor
public class ReplyController {

    private ReplyService service;

    // 요청이 /replies/new로 오면,
    // 정보를 조회해서 리턴 하는데, 정보 형태는 json이고, 전달 결과물은
    // 평범한 문자열 형태.

    @PostMapping(value="/new", consumes = "application/json"
            , produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
        // @RequestBody는 json 형태로 받은 값을 객체로 변환
        log.info("ReplyVO: " + vo);
        int insertCount = service.register(vo);
        log.info("Reply insert count: " + insertCount);

        return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // ResponseEntity : 웹페이지 생성(상태코드, 헤더, 응답, 데이터)
        // 3항 연산자 이용.
        // HttpStatus 페이지 상태를 전달.
        // 리턴에 코드는 길지만, 풀이하면
        // 정상 처리되면 정상 처리의 status 전달하고, 아니면 오류 status 전달.
    }

//    @GetMapping(value="/pages/{bno}/{page}",
//            produces= {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<List<ReplyVO>> getList(
//            @PathVariable("page") int page,
//            @PathVariable("bno") Long bno){
//        // @PathVariable : url로 넘겨받은 값 이용
//        log.info("getList......");
//        Criteria cri = new Criteria(page, 10);
//        log.info(cri);
//
//        return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
//        // T<List<ReplyVO>> t = new T<> ();
//        // 댓글 목록을 출력하고, 정상 처리 상태를 리턴.
//    }
    @GetMapping(value = "/{rno}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
        log.info("get: " + rno);
        return new ResponseEntity<>(
                service.get(rno), HttpStatus.OK);

    }


    @DeleteMapping(value="/{rno}"
            , produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> remove(
            @PathVariable("rno") Long rno){
        log.info("remove: " + rno);

        return service.remove(rno) == 1 ?
                new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
