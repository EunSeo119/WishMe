package com.wishme.user.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchSchoolResponseDto {

    private int schoolSeq;

    private String schoolName;

    private String region;

    private String uuid;

}
