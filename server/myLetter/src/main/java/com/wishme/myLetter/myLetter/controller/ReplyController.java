package com.wishme.myLetter.myLetter.controller;

import com.wishme.myLetter.myLetter.dto.request.SaveMyLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.request.SaveReplyRequestDto;
import com.wishme.myLetter.myLetter.service.ReplyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Getter
@RequestMapping("/api/my/reply")
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 개인 편지 답장 저장하기
     */
    @PostMapping("/write")
    public ResponseEntity<?> saveReply(@Valid @RequestBody SaveReplyRequestDto saveReplyRequestDto, Authentication authentication) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(replyService.saveReply(saveReplyRequestDto, authentication));
    }

//    public ResponseEntity<?>
}
