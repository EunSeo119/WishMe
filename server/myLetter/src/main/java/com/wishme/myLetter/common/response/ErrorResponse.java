package com.wishme.myLetter.common.response;

import com.wishme.myLetter.common.exception.code.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    // 에러 상태 코드
    private int statusCode;

    // 에러 구분 코드
    private String divisionCode;

    // 에러 메시지
    private String resultMsg;

//    // 상세 에러 메시지
//    private List<FieldError> errors;

    // 에러 이유
    private String reason;

    /**
     * ErrorResponse 생성자-1
     * @param code ErrorCode
     */
    @Builder
    protected ErrorResponse(final ErrorCode code) {
        this.resultMsg = code.getMessage();
        this.statusCode = code.getStatus();
        this.divisionCode = code.getDivisionCode();
//        this.errors = new ArrayList<>();
    }

    /**
     * ErrorResponse 생성자-2
     * @param code ErrorCode
     * @param reason String
     */
    @Builder
    protected ErrorResponse(final ErrorCode code, final String reason) {
        this.resultMsg = code.getMessage();
        this.statusCode = code.getStatus();
        this.divisionCode = code.getDivisionCode();
        this.reason = reason;
    }

    /**
     * Global Exception 전송 타입-2
     * @param code ErrorCode
     * @return ErrorResponse
     */
    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    /**
     * Global Exception 전송 타입-3
     * @param code ErrorCode
     * @param reason String
     * @return ErrorResponse
     */
    public static ErrorResponse of(final ErrorCode code, final String reason) {
        return new ErrorResponse(code, reason);
    }

}
