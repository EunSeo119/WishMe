package com.wishme.myLetter.myLetter.dto.request;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMyLetterRequestDto {

    private Long assetSeq;

    private String nickname;

    private String content;

    private Boolean isPublic;

    private Long toUser;

    // 얘는 추후에 시큐리티가 구현 완료되면 '@AuthenticationPrincipal UserDetails userDetails' 로 대체해주기
    private Long fromUser;
}
