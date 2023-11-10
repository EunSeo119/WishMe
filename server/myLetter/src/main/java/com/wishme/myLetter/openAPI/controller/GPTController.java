package com.wishme.myLetter.openAPI.controller;

import com.wishme.myLetter.openAPI.dto.request.GPTCompletionChatRequestDto;
import com.wishme.myLetter.openAPI.dto.response.GPTCompletionChatResponseDto;
import com.wishme.myLetter.openAPI.service.GPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt/")
public class GPTController {

    private final GPTService gptService;

    @PostMapping("chat")
    public String completionChat(final @RequestBody GPTCompletionChatRequestDto gptCompletionChatRequestDto) {

        return gptService.completionChat(gptCompletionChatRequestDto);
    }
}
