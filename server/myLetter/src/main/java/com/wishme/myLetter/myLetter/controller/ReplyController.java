package com.wishme.myLetter.myLetter.controller;

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

    /**
     * 편지 답장 리스트 확인하기
     */
    @GetMapping("/all")
    public ResponseEntity<?> getMyReplyList(Authentication authentication, @RequestParam(value = "page") int page) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(replyService.getMyReplyList(authentication, page));
    }

    /**
     * 편지 답장 상세내용 확인하기
     */
    @GetMapping("/detail/{replySeq}")
    public ResponseEntity<?> getReplyDetail(Authentication authentication, @PathVariable("replySeq") Long replySeq) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(replyService.getReplyDetail(authentication, replySeq));
    }
}
