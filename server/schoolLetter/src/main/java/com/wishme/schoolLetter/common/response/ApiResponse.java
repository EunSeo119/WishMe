package com.wishme.schoolLetter.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
/**
 * 이 클래스는 일단은 안쓰지만 추후에 success response 형식도 만들어서 보내고 싶으면 쓰기
 */
public class ApiResponse<T> {

    private T result;

    private int statusCode;

    public ApiResponse(T result) {
        this.result = result;
        statusCode = 200;
    }
}
