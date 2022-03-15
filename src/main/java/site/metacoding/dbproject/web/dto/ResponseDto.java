package site.metacoding.dbproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//data에 뭘담아야할지 모르니깐 제네릭으로 담는다
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {

    private Integer code; // -1. 실패, 1. 성공
    private String msg;

    // 응답의 body 데이터를 담는 곳
    private T data;
}
