package com.example.myblog.dto;
import com.example.myblog.utils.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BlogDto<T> {
//    private final ResponseStatus status;
    private final String status;
//    private final String message;
    private final T data;

//    public BlogDto(int sucess){
//        if (sucess < 0) {
//            this.message = "비밀번호를 다시 확인하세요.";
//        }
//        else this.message = "성공했습니다.";
//    }
}
