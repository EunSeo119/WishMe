package com.wishme.myLetter.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * *********************************************************************************************
     */
    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "G003", " Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "G005", "I/O Exception"),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "G006", "JsonParseException"),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "G008", "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "G009", "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "G010", "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "G011", "handle Validation Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "G012", "Header에 데이터가 존재하지 않는 경우 "),

    // 서버로 요청한 데이터가 존재하지 않음
    NOT_FOUND_DATA_ERROR(404, "G013", "Not Found Data Exception"),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),

    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    KAKAO_LOGIN_ERROR(400, "A001", "KAKAO Login Error Exception"),

    NONE_SOCIAL_ERROR(400, "U001", "존재 하지 않는 소셜로그인 요청"),

    NOT_AUTHENTICATED_ERROR(400, "U002", "인가되지 않은 사용자"),

    UNAUTHORIZED_USER_ERROR(400, "M001", "해당 데이터에 접근할 수 없는 사용자"),

    ALREADY_CREATED_ERROR(400, "R001", "이미 답장을 완료한 편지"),

    LETTER_NOT_FOUND_ERROR(404, "D014", "편지를 찾을 수 없습니다."),

    NOT_DEVELOPER_LETTER_ERROR(403, "D015", "개발자 편지가 아닙니다."),

    PRIVATE_LETTER_ERROR(400, "D016", "비공개 편지입니다.");


    /**
     * ******************************* Error Code Constructor ***************************************
     */
    private final Integer status;

    private final String divisionCode;

    private final String message;
}
