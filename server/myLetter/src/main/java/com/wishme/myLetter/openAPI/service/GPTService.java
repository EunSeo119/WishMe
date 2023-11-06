package com.wishme.myLetter.openAPI.service;

import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import com.wishme.myLetter.openAPI.dto.request.GPTCompletionChatRequestDto;
import com.wishme.myLetter.openAPI.dto.response.GPTCompletionChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GPTService {

    private final OpenAiService openAiService;

    @Transactional
    public String completionChat(GPTCompletionChatRequestDto gptCompletionChatRequestDto) {


        String inputMessage = gptCompletionChatRequestDto.getMessage();
        String defaultMessage = "\n" +
                "위 내용이 부정적인 내용을 담고 있으면 1 이라고 답해주고 그렇지 않으면 0 이라고 답해줘";

        gptCompletionChatRequestDto.setMessage(inputMessage + defaultMessage);

        System.out.println(inputMessage+defaultMessage);

        ChatCompletionResult chatCompletion = null;
        try {
            chatCompletion = openAiService.createChatCompletion(GPTCompletionChatRequestDto.of(gptCompletionChatRequestDto));
        } catch (Exception e) {
            return "pass-timeout";
        }

        GPTCompletionChatResponseDto response = GPTCompletionChatResponseDto.of(chatCompletion);

        List<String> messageList = response.getMessages().stream()
                .map(GPTCompletionChatResponseDto.Message::getMessage)
                .collect(Collectors.toList());

        System.out.println(messageList.stream().filter(Objects::nonNull).collect(Collectors.joining()));

        String checkLetter = messageList.stream().filter(Objects::nonNull).collect(Collectors.joining());

        if(checkLetter.equals("1")) {
            throw new IllegalArgumentException("부정적인 표현이 사용되었습니다.");
        }

        return "pass";
    }
}

