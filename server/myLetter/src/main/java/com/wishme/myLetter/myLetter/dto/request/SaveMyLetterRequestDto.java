package com.wishme.myLetter.myLetter.dto.request;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMyLetterRequestDto {

    @NotNull(message = "assetSeq은 빈값이 올 수 없습니다")
    private Long assetSeq;

    @NotBlank(message = "nickname은 빈값이 올 수 없습니다")
    private String nickname;

    @NotBlank(message = "content은 빈값이 올 수 없습니다")
    private String content;

    @NotNull(message = "isPublic은 빈값이 올 수 없습니다")
    private Boolean isPublic;

    @NotNull(message = "toUser은 빈값이 올 수 없습니다")
    private Long toUser;

    // 얘는 추후에 시큐리티가 구현 완료되면 '@AuthenticationPrincipal UserDetails userDetails' 로 대체해주기
    private Long fromUser;
}
